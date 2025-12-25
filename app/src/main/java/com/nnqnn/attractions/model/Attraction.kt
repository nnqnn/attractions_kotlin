package com.nnqnn.attractions.model

data class MapBounds(
    val minLat: Double,
    val maxLat: Double,
    val minLon: Double,
    val maxLon: Double
)

enum class AttractionCategory(val label: String, val emoji: String) {
    MUSEUM("Музей", "🏛"),
    EXHIBITION("Выставка", "🖼"),
    PARK("Парк", "🌳"),
    VIEWPOINT("Панорама", "👀"),
    RELIGION("Храм", "🕌"),
    FAMILY("Развлечения", "🎡"),
    HISTORY("История", "📜")
}

data class ScheduleItem(
    val day: String,
    val hours: String,
    val note: String? = null
)

data class ContactInfo(
    val phone: String? = null,
    val website: String? = null,
    val email: String? = null
)

data class Attraction(
    val id: Int,
    val name: String,
    val category: AttractionCategory,
    val shortDescription: String,
    val description: String,
    val address: String,
    val coords: List<Double>,
    val schedule: List<ScheduleItem>,
    val price: String = "Уточнить",
    val tags: List<String> = emptyList(),
    val contact: ContactInfo = ContactInfo(),
    val metro: String? = null,
    val rating: Double? = null,
    val imageUrl: String? = null
)

data class WeatherInfo(
    val temperature: Double,
    val windSpeed: Double,
    val time: String
)

