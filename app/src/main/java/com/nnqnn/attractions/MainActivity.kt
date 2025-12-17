package com.nnqnn.attractions

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Museum
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material3.AssistChip
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nnqnn.attractions.data.MockAttractions
import com.nnqnn.attractions.data.todayHours
import com.nnqnn.attractions.model.Attraction
import com.nnqnn.attractions.model.AttractionCategory
import com.nnqnn.attractions.model.MapBounds
import com.nnqnn.attractions.ui.theme.AttractionsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AttractionsTheme {
                AttractionsApp()
            }
        }
    }
}

private enum class BottomTab(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    LIST("Список", Icons.AutoMirrored.Filled.List),
    MAP("Карта", Icons.Default.Map),
    FAVORITES("Избранное", Icons.Default.Favorite)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AttractionsApp() {
    val attractions = remember { MockAttractions.items }
    var query by rememberSaveable { mutableStateOf("") }
    var selectedCategory by rememberSaveable { mutableStateOf<AttractionCategory?>(null) }
    var selectedTab by rememberSaveable { mutableStateOf(BottomTab.LIST) }
    var favorites by rememberSaveable { mutableStateOf(setOf<Int>()) }
    var selectedAttraction by remember { mutableStateOf<Attraction?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val filtered = remember(attractions, query, selectedCategory) {
        val trimmed = query.trim()
        attractions.filter { attraction ->
            val matchesQuery = if (trimmed.isBlank()) {
                true
            } else {
                attraction.name.contains(trimmed, ignoreCase = true) ||
                        attraction.address.contains(trimmed, ignoreCase = true) ||
                        attraction.tags.any { it.contains(trimmed, ignoreCase = true) }
            }
            val matchesCategory = selectedCategory?.let { attraction.category == it } ?: true
            matchesQuery && matchesCategory
        }
    }

    val visibleList = if (selectedTab == BottomTab.FAVORITES) {
        filtered.filter { favorites.contains(it.id) }
    } else {
        filtered
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Казань: музеи и места", fontWeight = FontWeight.Bold)
                        Text(
                            text = "Путеводитель без интернета",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },
                actions = {
                    if (query.isNotBlank() || selectedCategory != null) {
                        TextButton(onClick = {
                            query = ""
                            selectedCategory = null
                        }) {
                            Text("Сброс")
                        }
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                BottomTab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        icon = { Icon(tab.icon, contentDescription = tab.label) },
                        label = { Text(tab.label) }
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 12.dp)
        ) {
            SearchAndFilters(
                query = query,
                onQueryChange = { query = it },
                selectedCategory = selectedCategory,
                onCategoryChange = { category ->
                    selectedCategory = if (selectedCategory == category) null else category
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            when (selectedTab) {
                BottomTab.LIST, BottomTab.FAVORITES -> AttractionList(
                    attractions = visibleList,
                    favorites = favorites,
                    onSelect = { selectedAttraction = it },
                    onToggleFavorite = { id -> favorites = favorites.toggle(id) }
                )
                BottomTab.MAP -> MapScreen(
                    attractions = visibleList,
                    favorites = favorites,
                    onSelect = { selectedAttraction = it },
                    onToggleFavorite = { id -> favorites = favorites.toggle(id) }
                )
            }
        }
    }

    if (selectedAttraction != null) {
        ModalBottomSheet(
            onDismissRequest = { selectedAttraction = null },
            sheetState = sheetState
        ) {
            AttractionDetails(
                attraction = selectedAttraction!!,
                isFavorite = favorites.contains(selectedAttraction!!.id),
                onFavoriteToggle = {
                    favorites = favorites.toggle(selectedAttraction!!.id)
                }
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SearchAndFilters(
    query: String,
    onQueryChange: (String) -> Unit,
    selectedCategory: AttractionCategory?,
    onCategoryChange: (AttractionCategory) -> Unit
) {
    Column {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            placeholder = { Text("Поиск по названию, адресу или тегу") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        FlowRow(
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
        ) {
            AttractionCategory.entries.forEach { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = { onCategoryChange(category) },
                    label = { Text("${category.emoji} ${category.label}") }
                )
            }
        }
    }
}

@Composable
private fun AttractionList(
    attractions: List<Attraction>,
    favorites: Set<Int>,
    onSelect: (Attraction) -> Unit,
    onToggleFavorite: (Int) -> Unit
) {
    if (attractions.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Ничего не найдено по фильтрам")
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp)
        ) {
            items(attractions, key = { it.id }) { attraction ->
                AttractionCard(
                    attraction = attraction,
                    isFavorite = favorites.contains(attraction.id),
                    onFavoriteToggle = { onToggleFavorite(attraction.id) },
                    onClick = { onSelect(attraction) }
                )
            }
        }
    }
}

@Composable
private fun AttractionCard(
    attraction: Attraction,
    isFavorite: Boolean,
    onFavoriteToggle: () -> Unit,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        tonalElevation = 1.dp,
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = attraction.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onFavoriteToggle) {
                    val icon = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                    val tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    Icon(icon, contentDescription = "Избранное", tint = tint)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = categoryIcon(attraction.category),
                    contentDescription = null,
                    tint = categoryColor(attraction.category)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("${attraction.category.label} • ${attraction.address}", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = attraction.shortDescription,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Сегодня: ${attraction.schedule.todayHours()} • ${attraction.price}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            TagRow(attraction.tags)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TagRow(tags: List<String>, limit: Int = 3) {
    if (tags.isEmpty()) return
    FlowRow(
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(4.dp)
    ) {
        tags.take(limit).forEach { tag ->
            AssistChip(onClick = {}, label = { Text("#$tag") })
        }
        if (tags.size > limit) {
            AssistChip(onClick = {}, label = { Text("+${tags.size - limit}") })
        }
    }
}

@Composable
private fun MapScreen(
    attractions: List<Attraction>,
    favorites: Set<Int>,
    onSelect: (Attraction) -> Unit,
    onToggleFavorite: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(12.dp)
    ) {
        MapCard(
            attractions = attractions,
            bounds = MockAttractions.bounds,
            favorites = favorites,
            onSelect = onSelect
        )
        if (attractions.isNotEmpty()) {
            Text(
                text = "Кликай по точке на карте, чтобы открыть подробности. Цвет маркера зависит от типа локации.",
                style = MaterialTheme.typography.bodySmall
            )
            attractions.take(3).forEach { attraction ->
                MiniRow(attraction, isFavorite = favorites.contains(attraction.id), onToggleFavorite)
            }
        }
    }
}

@Composable
private fun MiniRow(
    attraction: Attraction,
    isFavorite: Boolean,
    onToggleFavorite: (Int) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = 1.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(attraction.name, fontWeight = FontWeight.SemiBold)
                Text(
                    "${attraction.category.label} • ${attraction.schedule.todayHours()}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            IconButton(onClick = { onToggleFavorite(attraction.id) }) {
                val icon = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                Icon(icon, contentDescription = "Избранное")
            }
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
private fun MapCard(
    attractions: List<Attraction>,
    bounds: MapBounds,
    favorites: Set<Int>,
    onSelect: (Attraction) -> Unit
) {
    val density = LocalDensity.current
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.1f)
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        val widthPx = with(density) { maxWidth.toPx() }
        val heightPx = with(density) { maxHeight.toPx() }
        val positions = remember(attractions, widthPx, heightPx) {
            attractions.associateWith {
                projectOnMap(
                    lat = it.coords.first,
                    lon = it.coords.second,
                    bounds = bounds,
                    width = widthPx,
                    height = heightPx
                )
            }
        }
        val gridColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f)
        val gridStroke = Stroke(width = 2f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(16f, 16f)))
        Canvas(
            modifier = Modifier
                .matchParentSize()
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            for (i in 1 until 4) {
                val x = size.width * i / 4
                drawLine(
                    gridColor,
                    start = androidx.compose.ui.geometry.Offset(x, 0f),
                    end = androidx.compose.ui.geometry.Offset(x, size.height),
                    strokeWidth = gridStroke.width,
                    pathEffect = gridStroke.pathEffect
                )
                val y = size.height * i / 4
                drawLine(
                    gridColor,
                    start = androidx.compose.ui.geometry.Offset(0f, y),
                    end = androidx.compose.ui.geometry.Offset(size.width, y),
                    strokeWidth = gridStroke.width,
                    pathEffect = gridStroke.pathEffect
                )
            }
        }
        positions.forEach { (attraction, offsetPx) ->
            val xDp = with(density) { offsetPx.x.toDp() }
            val yDp = with(density) { offsetPx.y.toDp() }
            val color = categoryColor(attraction.category)
            val size = if (favorites.contains(attraction.id)) 30.dp else 24.dp
            Box(
                modifier = Modifier
                    .offset(x = xDp - size / 2, y = yDp - size / 2)
                    .size(size)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.15f))
                    .border(
                        width = 2.dp,
                        color = color,
                        shape = CircleShape
                    )
                    .clickable { onSelect(attraction) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = categoryIcon(attraction.category),
                    contentDescription = attraction.name,
                    tint = color
                )
            }
        }
    }
}

@Composable
private fun AttractionDetails(
    attraction: Attraction,
    isFavorite: Boolean,
    onFavoriteToggle: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = categoryIcon(attraction.category),
                contentDescription = null,
                tint = categoryColor(attraction.category)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = attraction.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onFavoriteToggle) {
                val icon = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                Icon(icon, contentDescription = "В избранное")
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(attraction.shortDescription, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        TagRow(attraction.tags, limit = 6)
        Spacer(modifier = Modifier.height(12.dp))
        Text("Расписание", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
        attraction.schedule.forEach { slot ->
            Text("${slot.day}: ${slot.hours}${slot.note?.let { " (${it})" } ?: ""}")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text("Стоимость: ${attraction.price}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Адрес: ${attraction.address}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Контакты", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
        attraction.contact.phone?.let { Text("Телефон: $it") }
        attraction.contact.website?.let { Text("Сайт: $it") }
        attraction.contact.email?.let { Text("Email: $it") }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Сегодня: ${attraction.schedule.todayHours()}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}

private fun categoryIcon(category: AttractionCategory) = when (category) {
    AttractionCategory.MUSEUM -> Icons.Default.Museum
    AttractionCategory.EXHIBITION -> Icons.Default.Brush
    AttractionCategory.PARK -> Icons.Default.Park
    AttractionCategory.VIEWPOINT -> Icons.Default.TravelExplore
    AttractionCategory.RELIGION -> Icons.Default.AccountBalance
    AttractionCategory.FAMILY -> Icons.Default.FavoriteBorder
    AttractionCategory.HISTORY -> Icons.Default.History
}

private fun categoryColor(category: AttractionCategory): Color = when (category) {
    AttractionCategory.MUSEUM -> Color(0xFF4E7FFF)
    AttractionCategory.EXHIBITION -> Color(0xFFB45FE6)
    AttractionCategory.PARK -> Color(0xFF2D9D4A)
    AttractionCategory.VIEWPOINT -> Color(0xFF00838F)
    AttractionCategory.RELIGION -> Color(0xFFCC7722)
    AttractionCategory.FAMILY -> Color(0xFFEF6C00)
    AttractionCategory.HISTORY -> Color(0xFF455A64)
}

private fun projectOnMap(
    lat: Double,
    lon: Double,
    bounds: MapBounds,
    width: Float,
    height: Float
): androidx.compose.ui.geometry.Offset {
    val xRatio = ((lon - bounds.minLon) / (bounds.maxLon - bounds.minLon)).toFloat().coerceIn(0f, 1f)
    val yRatio = 1f - ((lat - bounds.minLat) / (bounds.maxLat - bounds.minLat)).toFloat().coerceIn(0f, 1f)
    return androidx.compose.ui.geometry.Offset(
        x = xRatio * width,
        y = yRatio * height
    )
}

private fun Set<Int>.toggle(id: Int): Set<Int> = if (contains(id)) {
    minus(id)
} else {
    plus(id)
}