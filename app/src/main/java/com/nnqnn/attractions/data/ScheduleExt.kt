package com.nnqnn.attractions.data

import com.nnqnn.attractions.model.ScheduleItem
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Locale

fun List<ScheduleItem>.todayHours(): String {
    val todayAbbr = LocalDate.now().dayOfWeek.toRuShort()
    val normalized = firstOrNull { it.matches(todayAbbr) }
    return normalized?.hours ?: firstOrNull()?.hours ?: "Уточнить"
}

private fun ScheduleItem.matches(todayAbbr: String): Boolean {
    val normalized = day.lowercase(Locale("ru"))
        .replace("–", "-")
        .replace("—", "-")
    if (normalized.contains("ежеднев")) return true
    if (normalized.contains("круглосуточ")) return true
    if (normalized.contains(todayAbbr)) return true
    if (normalized.contains("-")) {
        val parts = normalized.split("-").map { it.trim().take(2) }
        if (parts.size == 2) {
            val order = ruDayOrder()
            val start = order.indexOf(parts[0])
            val end = order.indexOf(parts[1])
            val target = order.indexOf(todayAbbr)
            if (start != -1 && end != -1 && target != -1) {
                if (start <= end && target in start..end) return true
                if (start > end && (target >= start || target <= end)) return true
            }
        }
    }
    return false
}

private fun DayOfWeek.toRuShort(): String = when (this) {
    DayOfWeek.MONDAY -> "пн"
    DayOfWeek.TUESDAY -> "вт"
    DayOfWeek.WEDNESDAY -> "ср"
    DayOfWeek.THURSDAY -> "чт"
    DayOfWeek.FRIDAY -> "пт"
    DayOfWeek.SATURDAY -> "сб"
    DayOfWeek.SUNDAY -> "вс"
}

private fun ruDayOrder(): List<String> = listOf("пн", "вт", "ср", "чт", "пт", "сб", "вс")

