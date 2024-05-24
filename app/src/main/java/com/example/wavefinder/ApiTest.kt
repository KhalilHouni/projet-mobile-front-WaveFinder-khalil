package com.example.wavefinder

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.wavefinder.apiservice.ApiClient
import com.example.wavefinder.model.SurfResponse
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.util.*

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ApiTest() {
    val spots = remember { mutableStateListOf<SurfResponse.Record>() }
    val scope = rememberCoroutineScope()

    // Récupérer les données de l'API
    LaunchedEffect(Unit) {
        try {
            val response = ApiClient.apiService.getSurfSpots()
            spots.addAll(response)
            Log.d("API Hello", "The API call worked !")
        } catch (e: Exception) {
            Log.e("API_ERROR", "Error fetching data: ${e.message}")
        }
    }

    // Fonction pour supprimer un spot de l'API
    suspend fun deleteSpot(record: SurfResponse.Record) {
        try {
            record.id?.let { ApiClient.apiService.deleteSurfSpot(it) }
            spots.remove(record)
            Log.d("API Delete", "Spot deleted successfully!")
        } catch (e: Exception) {
            Log.e("API_ERROR", "Error deleting spot: ${e.message}")
        }
    }

    // Afficher les données dans une LazyColumn
    LazyColumn {
        items(spots) { record ->
            SpotItem(record = record) {
                // Supprimer le spot lorsque la croix est cliquée
                scope.launch {
                    deleteSpot(record)
                }
            }
        }
    }
}

@Composable
fun SpotItem(record: SurfResponse.Record, onDeleteClicked: () -> Unit) {
    val showDetails = remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text(text = "Confirmation de suppression") },
            text = { Text("Êtes-vous sûr de vouloir effacer ce spot ? Cette action est irréversible.") },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteClicked()
                        showDialog.value = false
                    }
                ) {
                    Text("Oui")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog.value = false }
                ) {
                    Text("Non")
                }
            }
        )
    }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
    ) {
        Box {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Destination: ${record.fields?.destination ?: "Pas de données dispo !"}")
                Text(text = "Surf Break: ${record.fields?.surfBreak?.joinToString() ?: "Pas de données dispo !"}")
                if (record.fields?.photos?.isNotEmpty() == true) {
                    record.fields.photos.first().url?.let { ImageFromUrl(photoUrl = it) }
                } else {
                    Text(text = "Donnée indisponible !")
                }
                Text(text = "Address: ${record.fields?.address ?: "Pas de données dispo !"}")

                Button(onClick = { showDetails.value = !showDetails.value }) {
                    Text(text = if (showDetails.value) "Masquer les détails" else "Afficher les détails")
                }

                if (showDetails.value) {
                    val magicSeaweedLink = record.fields?.magicSeaweedLink
                    if (!magicSeaweedLink.isNullOrEmpty()) {
                        val annotatedString = buildAnnotatedString {
                            pushStyle(SpanStyle(color = MaterialTheme.colorScheme.primary, fontSize = 16.sp, textDecoration = TextDecoration.Underline))
                            append("MagicSeaweed Link")
                            pop()
                        }

                        ClickableText(
                            text = annotatedString,
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(magicSeaweedLink))
                                context.startActivity(intent)
                            },
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    } else {
                        Text(text = "Pas d' info disponible !")
                    }

                    // Geocode handling
                    val geocode = record.fields?.geocode
                    if (!geocode.isNullOrEmpty()) {
                        val decodedGeocode = runCatching { String(Base64.getDecoder().decode(geocode)) }.getOrNull()
                        val geocodeJson = runCatching { Json.parseToJsonElement(decodedGeocode!!).jsonObject }.getOrNull()
                        val lat = geocodeJson?.get("o")?.jsonObject?.get("lat")?.jsonPrimitive?.doubleOrNull
                        val lng = geocodeJson?.get("o")?.jsonObject?.get("lng")?.jsonPrimitive?.doubleOrNull

                        if (lat != null && lng != null) {
                            val googleMapsUrl = "https://www.google.com/maps?q=$lat,$lng"

                            val geocodeAnnotatedString = buildAnnotatedString {
                                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary, textDecoration = TextDecoration.Underline)) {
                                    append("Voir sur Google Maps")
                                }
                            }

                            ClickableText(
                                text = geocodeAnnotatedString,
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(googleMapsUrl))
                                    context.startActivity(intent)
                                },
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        } else {
                            Text(text = "Pas de données géographiques disponibles !")
                        }
                    } else {
                        Text(text = "Pas de données géographiques disponibles !")
                    }

                    Text(text = "Influencers: ${record.fields?.influencers?.joinToString() ?: "Pas de données dispo !"}")
                    record.fields?.difficultyLevel?.let { DifficultyRatingBar(difficultyLevel = it) } ?: Text(text = "Pas de données dispo !")
                }
            }

            // Afficher l'icône de suppression uniquement si l'ID commence par "userspot_"
            if (record.id?.startsWith("userspot_") == true) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = { showDialog.value = true },
                        modifier = Modifier
                            .size(24.dp)
                            .padding(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = Color.Red
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DifficultyRatingBar(difficultyLevel: Int) {
    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        repeat(5) { index ->
            Icon(
                imageVector = if (index < difficultyLevel) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = null,
                tint = if (index < difficultyLevel) Color.Yellow else Color.Gray,
                modifier = Modifier.size(24.dp)
            )
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
