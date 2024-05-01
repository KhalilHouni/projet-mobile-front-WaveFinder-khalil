package com.example.wavefinder

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SpotListScreen(navController: NavHostController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Text(text = "Liste des spots de surf")
        }

        items(fakeSpots) { spot ->
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
            Text(text = spot.name)

            Image(
                painter = painterResource(id = spot.imageResId),
                contentDescription = "Image du spot: ${spot.name}",
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            )

            Text(
                text = "Emplacement: ${spot.location}",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun SpotListPreview() {
    val fakeSpots = listOf(
        Spot("Spot 1", R.drawable.spot1, "Hawaii"),
        Spot("Spot 2", R.drawable.spot2, "California"),
        Spot("Spot 3", R.drawable.spot3, "Australia"),
        Spot("Spot 4", R.drawable.spot4, "Indonesia")
        // Add more spots as needed
    )

    val lazyListState = rememberLazyListState()

    Surface {
        LazyColumn(state = lazyListState) {
            items(fakeSpots) { spot ->
                SpotItem(spot = spot)
            }
        }
    }
}