package com.example.wavefinder

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.beust.klaxon.Klaxon
import com.example.wavefinder.model.Welcome

fun readJSONFromAsset(context: Context, fileName: String): String {
    val inputStream = context.assets.open(fileName)
    return inputStream.bufferedReader().use { it.readText() }
}

@Composable
fun SpotListScreen(navController: NavHostController) {
    val context = LocalContext.current
    val spots = remember { getSpotsFromJSON(context) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text("Liste des spots de surf")
        }

        items(spots) { spot ->
            SpotItem(spot = spot)
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Button(
                onClick = { navController.navigate("homeScreen") },
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
            ) {
                Text("Revenir à l'accueil")
            }
        }
    }
}

fun getSpotsFromJSON(context: Context): List<Spot> {
    val json = readJSONFromAsset(context, "spots.json")
    val spotListResponse = Klaxon().parse<Welcome>(json)

    return spotListResponse?.records?.map { record ->
        val surfBreak = record.fields.surfBreak.joinToString()
        val photos = record.fields.photos.map { it.url }
        val address = record.fields.address
        Spot(surfBreak, photos, address)
    } ?: emptyList()
}


@Composable
fun SpotItem(spot: Spot) {
    Surface(
        color = Color.Blue,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Surf Break: ${spot.surfBreak}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            ImageFromUrl(photoUrl = spot.photos.firstOrNull() ?: "", modifier = Modifier.fillMaxWidth())

            Text(
                text = "Destination: ${spot.address}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Button(
                onClick = { /* Handle button click */ },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Voir les détails")
            }
        }
    }
}

@Composable
fun ImageFromUrl(photoUrl: String, modifier: Modifier = Modifier) {
    val imagePainter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(photoUrl)
            .crossfade(true)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .build()
    )

    Box(
        modifier = modifier.height(200.dp)
    ) {
        Image(
            painter = imagePainter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
