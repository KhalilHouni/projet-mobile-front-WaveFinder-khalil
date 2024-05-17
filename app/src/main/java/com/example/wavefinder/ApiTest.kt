package com.example.wavefinder

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.wavefinder.apiservice.ApiClient
import com.example.wavefinder.model.Spot
import com.example.wavefinder.model.SurfResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch as launch1
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ApiTest() {
    val spots = remember { mutableStateListOf<SurfResponse.Record>() }

    LaunchedEffect(Unit) {
        try {
            val response = ApiClient.apiService.getSurfSpots()
            spots.addAll(response)
            Log.d("API Hello", "Does the Api works ?")
        } catch (e: Exception) {
            Log.e("API_ERROR", "Error fetching data: ${e.message}")
        }
    }

    LazyColumn {
        items(spots) { record ->
            SpotItem(record = record)
        }
    }
}



@Composable
fun SpotItem(record: SurfResponse.Record) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),

    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Destination: ${record.fields.destination}")
            Text(text = "Surf Break: ${record.fields.surfBreak.joinToString()}")
            ImageFromUrl(photoUrl = record.fields.photos.firstOrNull()?.url ?: "")
            Text(text = "Address: ${record.fields.address}")
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