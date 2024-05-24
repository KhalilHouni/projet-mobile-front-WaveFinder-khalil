package com.example.wavefinder

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.wavefinder.apiservice.ApiClient
import com.example.wavefinder.model.SurfResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val PREFS_NAME = "com.example.wavefinder.prefs"
private const val KEY_COUNTER = "key_counter"

fun getNextUserSpotId(context: Context): String {
    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val counter = sharedPreferences.getInt(KEY_COUNTER, 0) + 1
    sharedPreferences.edit().putInt(KEY_COUNTER, counter).apply()
    return "userspot_$counter"
}

@Composable
fun AddSpot(navController: NavHostController) {
    val context = LocalContext.current
    val destination = remember { mutableStateOf(TextFieldValue()) }
    val surfBreak = remember { mutableStateOf(TextFieldValue()) }
    val address = remember { mutableStateOf(TextFieldValue()) }
    val imageUrl = remember { mutableStateOf(TextFieldValue()) }
    val difficultyLevel = remember { mutableStateOf(1) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text("Ajouter un Spot")

        TextField(
            value = destination.value,
            onValueChange = { destination.value = it },
            label = { Text("Destination") }
        )

        TextField(
            value = surfBreak.value,
            onValueChange = { surfBreak.value = it },
            label = { Text("Surf Break") }
        )

        TextField(
            value = address.value,
            onValueChange = { address.value = it },
            label = { Text("Adresse") }
        )

        TextField(
            value = imageUrl.value,
            onValueChange = { imageUrl.value = it },
            label = { Text("URL de l'image") }
        )

        TextField(
            value = difficultyLevel.value.toString(),
            onValueChange = {
                // Ensure the difficulty level stays within 1-5
                val newValue = it.toIntOrNull() ?: 1
                difficultyLevel.value = newValue.coerceIn(1, 5)
            },
            label = { Text("Difficulté (1-5)") }
        )

        Button(
            onClick = {
                val photo = SurfResponse.Photo(
                    id = null,
                    width = null,
                    height = null,
                    url = imageUrl.value.text,
                    filename = null,
                    size = null,
                    type = null,
                    thumbnails = null
                )
                val fields = SurfResponse.Fields(
                    influencers = null,
                    peakSurfSeasonEnds = null,
                    difficultyLevel = difficultyLevel.value,
                    destination = destination.value.text,
                    geocode = null,
                    surfBreak = listOf(surfBreak.value.text),
                    magicSeaweedLink = null,
                    photos = listOf(photo),
                    peakSurfSeasonBegins = null,
                    destinationStateCountry = null,
                    address = address.value.text
                )
                val record = SurfResponse.Record(
                    id = getNextUserSpotId(context),
                    createdTime = null,
                    fields = fields
                )

                coroutineScope.launch(Dispatchers.IO) {
                    val success = addSpot(record)
                    if (success) {
                        snackbarHostState.showSnackbar("Spot ajouté !")
                    } else {
                        snackbarHostState.showSnackbar("Erreur lors de l'ajout du spot.")
                    }
                }
            },
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text("Ajouter Spot")
        }

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text("Retour Accueil")
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}

private suspend fun addSpot(record: SurfResponse.Record): Boolean {
    return try {
        ApiClient.apiService.addSurfSpot(record)
        true
    } catch (e: Exception) {
        e.printStackTrace() // Log the exception
        false
    }
}
