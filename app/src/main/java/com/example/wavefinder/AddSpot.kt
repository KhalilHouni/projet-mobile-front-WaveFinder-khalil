import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun AddSpot(navController: NavHostController) {

    val destination = remember { mutableStateOf(TextFieldValue()) }
    val surfBreak = remember { mutableStateOf(TextFieldValue()) }
    val address = remember { mutableStateOf(TextFieldValue()) }

    // Composable pour le formulaire
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text("Ajouter un Spot")

        Text("Destination:")

        TextField(
            value = destination.value,
            onValueChange = { destination.value = it }
        )


        Text("Surf Break:")

        TextField(
            value = surfBreak.value,
            onValueChange = { surfBreak.value = it }
        )


        Text("Adresse:")

        TextField(
            value = address.value,
            onValueChange = { address.value = it }
        )

        // Bouton pour ajouter le spot
        Button(
            onClick = {

                },
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text("Ajouter Spot")
        }

        // Bouton pour retourner à l'écran d'accueil
        Button(
            onClick = { navController.popBackStack() }, // Revenir à l'écran précédent
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text("Retour Accueil")
        }
    }
}
