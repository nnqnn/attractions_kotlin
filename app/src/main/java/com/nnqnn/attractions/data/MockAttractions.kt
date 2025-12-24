package com.nnqnn.attractions.data

import com.nnqnn.attractions.model.Attraction
import com.nnqnn.attractions.model.AttractionCategory
import com.nnqnn.attractions.model.ContactInfo
import com.nnqnn.attractions.model.MapBounds
import com.nnqnn.attractions.model.ScheduleItem
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Locale

object MockAttractions {
    val items: List<Attraction> = listOf(
        Attraction(
            id = 1,
            name = "Казанский кремль",
            category = AttractionCategory.HISTORY,
            shortDescription = "Белокаменная крепость с музеями и панорамой на Казанку",
            description = "Архитектурный комплекс под охраной ЮНЕСКО: башня Сююмбике, Благовещенский собор, мечеть Кул-Шариф и музей-заповедник. Отличная точка начала прогулки по центру.",
            address = "Кремлёвская ул., 2",
            coords = 55.7999 to 49.1076,
            schedule = listOf(
                ScheduleItem("Пн–Чт", "10:00–18:00"),
                ScheduleItem("Пт", "11:00–20:00"),
                ScheduleItem("Сб–Вс", "10:00–19:00")
            ),
            price = "от 200 ₽, музеи — отдельно",
            tags = listOf("UNESCO", "панорама", "музеи"),
            contact = ContactInfo(
                phone = "+7 (843) 567-80-00",
                website = "https://kazan-kremlin.ru"
            ),
            imageUrl = "https://kazan-kremlin.ru/wp-content/uploads/shutterstock_25079572-scaled.jpg",
            rating = 4.9
        ),
        Attraction(
            id = 2,
            name = "Мечеть Кул-Шариф",
            category = AttractionCategory.RELIGION,
            shortDescription = "Главная мечеть Татарстана внутри Кремля",
            description = "Современная мечеть с музейной экспозицией. Открыта для посещения вне времени молитв, можно подняться на балкон и увидеть интерьер.",
            address = "Кремлёвская ул., 13",
            coords = 55.7988 to 49.1044,
            schedule = listOf(
                ScheduleItem("Ежедневно", "09:00–19:30"),
                ScheduleItem("Намаз", "вход ограничен во время молитвы")
            ),
            price = "Бесплатно",
            tags = listOf("архитектура", "кремль"),
            contact = ContactInfo(
                phone = "+7 (843) 567-80-01",
                website = "https://kazan-kremlin.ru/objects/64"
            ),
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/d/d1/Kazan_Kremlin_Qolsharif_Mosque_08-2016_img2.jpg",
            rating = 4.9
        ),
        Attraction(
            id = 3,
            name = "Национальный музей РТ",
            category = AttractionCategory.MUSEUM,
            shortDescription = "Главный музей Татарстана с историческими коллекциями",
            description = "Экспозиции о Волжской Булгарии, Казанском ханстве, купеческой Казани и современной истории. Часто проводятся временные выставки.",
            address = "ул. Кремлёвская, 2",
            coords = 55.7921 to 49.1226,
            schedule = listOf(
                ScheduleItem("Вт–Вс", "10:00–18:00"),
                ScheduleItem("Пн", "Выходной")
            ),
            price = "от 250 ₽",
            tags = listOf("история", "детям", "центр"),
            contact = ContactInfo(
                phone = "+7 (843) 292-89-84",
                website = "https://tatmuseum.ru"
            ),
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/8/84/%D0%9D%D0%B0%D1%86%D0%B8%D0%BE%D0%BD%D0%B0%D0%BB%D1%8C%D0%BD%D1%8B%D0%B9_%D0%BC%D1%83%D0%B7%D0%B5%D0%B9_%D0%A0%D0%B5%D1%81%D0%BF%D1%83%D0%B1%D0%BB%D0%B8%D0%BA%D0%B8_%D0%A2%D0%B0%D1%82%D0%B0%D1%80%D1%81%D1%82%D0%B0%D0%BD.JPG",
            rating = 4.7
        ),
        Attraction(
            id = 4,
            name = "Казанская ратуша (Центр семьи «Казан») ",
            category = AttractionCategory.VIEWPOINT,
            shortDescription = "Смотровая площадка на берегу Казанки с панорамой центра",
            description = "Необычное здание в форме чаши. На верхних уровнях летом работает смотровая площадка, а вокруг — набережная, парк и инсталляции.",
            address = "ул. Сибгата Хакима, 4",
            coords = 55.8224 to 49.1142,
            schedule = listOf(
                ScheduleItem("Пт–Вс", "10:00–20:00"),
                ScheduleItem("Пн–Чт", "Закрыто на посещение, территория открыта")
            ),
            price = "300 ₽ смотровая площадка",
            tags = listOf("видовая точка", "набережная"),
            contact = ContactInfo(
                phone = "+7 (843) 520-00-00"
            ),
            imageUrl = "https://upload.wikimedia.org/wikipedia/ru/8/8f/%D0%A6%D0%B5%D0%BD%D1%82%D1%80_%D1%81%D0%B5%D0%BC%D1%8C%D0%B8_%D0%9A%D0%B0%D0%B7%D0%B0%D0%BD_1.jpg",
            rating = 4.6
        ),
        Attraction(
            id = 5,
            name = "Музей «Городская панорама»",
            category = AttractionCategory.EXHIBITION,
            shortDescription = "Миниатюры Казани с интерактивом и VR",
            description = "Две большие миниатюры дореволюционной и современной Казани, VR-зона, макеты улиц и герои в национальных костюмах. Отлично для семейного визита.",
            address = "ул. Декабристов, 2",
            coords = 55.7859 to 49.1222,
            schedule = listOf(
                ScheduleItem("Ежедневно", "10:00–19:00")
            ),
            price = "от 350 ₽",
            tags = listOf("интерактив", "семье", "центр"),
            contact = ContactInfo(
                phone = "+7 (843) 292-12-02",
                website = "https://panorama-kazan.ru"
            ),
            imageUrl = "https://kuda-kazan.ru/uploads/3e1a41fca6613548fade2d4fd95aea71.jpg",
            rating = 4.8
        ),
        Attraction(
            id = 6,
            name = "Музей чак-чака",
            category = AttractionCategory.MUSEUM,
            shortDescription = "Небольшой музей о татарской кухне с дегустацией",
            description = "Экскурсия рассказывает о традициях татарской выпечки, показывает старинную утварь и заканчивается чаепитием с чак-чаком и кыстыбый.",
            address = "ул. Парижской Коммуны, 18",
            coords = 55.7821 to 49.121,
            schedule = listOf(
                ScheduleItem("Ежедневно", "10:00–20:00"),
                ScheduleItem("По записи", "Группы лучше бронировать заранее")
            ),
            price = "600 ₽ с дегустацией",
            tags = listOf("гастрономия", "уютно", "семье"),
            contact = ContactInfo(
                phone = "+7 (843) 292-05-32",
                website = "https://chak-chakmuseum.ru"
            ),
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/7/72/%D0%9C%D1%83%D0%B7%D0%B5%D0%B9_%D1%87%D0%B0%D0%BA-%D1%87%D0%B0%D0%BA%D0%B0.jpg",
            rating = 4.7
        ),
        Attraction(
            id = 7,
            name = "Парк «Чёрное озеро»",
            category = AttractionCategory.PARK,
            shortDescription = "Исторический парк с озером, арт-объектами и катком зимой",
            description = "Пешеходные дорожки, летняя сцена, зона отдыха у воды. Часто проходят городские фестивали и уличные ярмарки.",
            address = "ул. Дзержинского, 7",
            coords = 55.7887 to 49.1213,
            schedule = listOf(
                ScheduleItem("Круглосуточно", "Свободный вход")
            ),
            price = "Бесплатно",
            tags = listOf("прогулки", "фестивали", "центр"),
            contact = ContactInfo(
                website = "https://kzn.ru"
            ),
            imageUrl = "https://kuda-kazan.ru/uploads/2e9874baa3d0cfa829eaf9464bc34fb4.jpg",
            rating = 4.6
        )
    )

    val bounds = MapBounds(
        minLat = items.minOf { it.coords.first } - 0.01,
        maxLat = items.maxOf { it.coords.first } + 0.01,
        minLon = items.minOf { it.coords.second } - 0.01,
        maxLon = items.maxOf { it.coords.second } + 0.01
    )
}

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
                if (start > end && (target >= start || target <= end)) return true // диапазон через воскресенье
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

