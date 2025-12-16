package com.nnqnn.attractions.v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nnqnn.attractions.data.MockAttractions
import com.nnqnn.attractions.data.todayHours
import com.nnqnn.attractions.model.AttractionCategory
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
    var onlyMuseums by rememberSaveable { mutableStateOf(false) }
    val items = MockAttractions.items.filter {
        !onlyMuseums || it.category == AttractionCategory.MUSEUM
    }
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Фильтр по музеям и список",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        AssistChip(
            onClick = { onlyMuseums = !onlyMuseums },
            label = { Text(if (onlyMuseums) "Показать все" else "Только музеи") },
            leadingIcon = null,
            colors = AssistChipDefaults.assistChipColors()
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(items, key = { it.id }) { attraction ->
                Card(
                    shape = CardDefaults.elevatedShape,
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(attraction.name, fontWeight = FontWeight.Bold)
                        Text(attraction.shortDescription, style = MaterialTheme.typography.bodyMedium)
                        Text("Сегодня: ${attraction.schedule.todayHours()}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

