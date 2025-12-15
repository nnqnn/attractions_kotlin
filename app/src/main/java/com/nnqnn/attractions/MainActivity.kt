package com.nnqnn.attractions

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nnqnn.attractions.ui.theme.AttractionsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AttractionsTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AttractionsApp()
                }
            }
        }
    }
}

@Composable
fun AttractionsApp() {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        items(simpleAttractions) { item ->
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = item.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                    Text(text = item.shortDescription, style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Адрес: ${item.address}", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

private data class SimpleAttraction(
    val name: String,
    val shortDescription: String,
    val address: String
)

private val simpleAttractions = listOf(
    SimpleAttraction(
        name = "Казанский кремль",
        shortDescription = "Кремль",
        address = "Кремлёвская ул., 2"
    ),
    SimpleAttraction(
        name = "Мечеть Кул-Шариф",
        shortDescription = "Главная мечеть Татарстана",
        address = "Кремлёвская ул., 13"
    ),
    SimpleAttraction(
        name = "Музей чак-чака",
        shortDescription = "Небольшой гастрономический музей",
        address = "Парижской Коммуны, 18"
    )
)

