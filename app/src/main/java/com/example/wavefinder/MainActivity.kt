package com.example.wavefinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wavefinder.ui.theme.WaveFinderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaveFinderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "homeScreen") {
                        composable("homeScreen") { HomeScreen(navController) }
                        composable("spotListScreen") { SpotListScreen(navController) }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Bannière colorée avec du texte et logo
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF87CEEB), // Couleur de la bannière
            contentColor = Color.Black, // Couleur du texte
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), // Padding autour de la bannière
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Find a spot to ride !",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.weight(1f) // Pour centrer le texte
                )

                Image(
                    painter = painterResource(id = R.drawable.wavefinderlogo),
                    contentDescription = "WaveFinder Logo",
                    modifier = Modifier.size(80.dp)
                )
            }
        }

        // Espace entre la bannière et l'image homepic
        Spacer(modifier = Modifier.height(16.dp))

        // Image Homepic dans un cadre
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.homepic),
                contentDescription = "WaveFinder Background Image",
                modifier = Modifier.fillMaxSize()
            )
        }

        // Bouton pour naviguer vers la liste des spots
        Button(
            onClick = { navController.navigate("spotListScreen") },
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text("Clique pour voir la liste des spots !")
        }

        // Text en bas avec "créer par Khalil, Adrien, Maud, Sabri"
        Text(
            text = "Créé par Khalil, Adrien, Maud, Sabri",
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}