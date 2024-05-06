package com.example.wavefinder

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Composable
fun SpotListScreen(navController: NavHostController) {
    val context = LocalContext.current
    val spots = remember { getSpotsFromJSON(context) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text(text = "Liste des spots de surf")
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
    val listType = object : TypeToken<List<Spot>>() {}.type
    return Gson().fromJson(json, listType)
}

fun readJSONFromAsset(context: Context, fileName: String): String {
    val inputStream = context.assets.open(fileName)
    return inputStream.bufferedReader().use { it.readText() }
}

@Composable
fun SpotItem(spot: Spot) {
    Surface(
        color = Color.LightGray,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Load the image painter with Coil
            val painter = // You can customize the image loading here if needed
                rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = spot.imageURL)
                        .apply(block = fun ImageRequest.Builder.() {
                            // You can customize the image loading here if needed
                        }).build()
                )

            // Display the image using Image composable
            Image(
                painter = painter,
                contentDescription = "Spot Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop // Adjust this as needed
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = spot.name,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Emplacement: ${spot.location}",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
