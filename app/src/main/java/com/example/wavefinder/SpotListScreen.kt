package com.example.wavefinder

import android.content.Context
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import coil.compose.rememberImagePainter
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest


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
    val jsonObject = JSONObject(json)
    val recordsArray = jsonObject.getJSONArray("records")

    val spots = mutableListOf<Spot>()
    val gson = Gson()
    for (i in 0 until recordsArray.length()) {
        val spotJson = recordsArray.getJSONObject(i).toString()
        val spot = gson.fromJson(spotJson, Spot::class.java)
        spots.add(spot)
    }
    return spots
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
            spot.surfBreak?.let { surfBreak ->
                Text(text = surfBreak)
                Spacer(modifier = Modifier.height(8.dp))
            }
            spot.address?.let { address ->
                Text(text = "Location: $address")
                Spacer(modifier = Modifier.height(8.dp))
            }
            spot.photos?.let { photoUrl ->
                ImageFromUrl(photoUrl = photoUrl)
            }
        }
    }
}

@Composable
fun ImageFromUrl(photoUrl: String) {
    val imagePainter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(photoUrl)
            .build()
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(200.dp)
            .width(200.dp)
    ) {
        Image(
            painter = imagePainter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}