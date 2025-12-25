package com.nnqnn.attractions.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.nnqnn.attractions.model.Attraction
import com.nnqnn.attractions.model.MapBounds
import kotlin.random.Random
import kotlin.math.max
import kotlin.math.min

class MapOverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val data = mutableListOf<Attraction>()
    private var bounds: MapBounds? = null
    var onSelect: ((Attraction) -> Unit)? = null

    fun setData(attractions: List<Attraction>, mapBounds: MapBounds) {
        data.clear()
        data.addAll(attractions)
        bounds = mapBounds
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // фон
        canvas.drawColor(Color.parseColor("#e5edf4"))

        // сетка
        val gridPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#90b0c7")
            style = Paint.Style.STROKE
            strokeWidth = 2f
        }
        val stepX = width / 6f
        val stepY = height / 6f
        for (i in 1 until 6) {
            canvas.drawLine(i * stepX, 0f, i * stepX, height.toFloat(), gridPaint)
            canvas.drawLine(0f, i * stepY, width.toFloat(), i * stepY, gridPaint)
        }

        val b = bounds
        val points = if (data.isNotEmpty() && b != null) {
            val minLon = b.minLon
            val maxLon = b.maxLon
            val minLat = b.minLat
            val maxLat = b.maxLat
            data.map { attraction ->
                val lat = attraction.coords.getOrNull(0) ?: 0.0
                val lon = attraction.coords.getOrNull(1) ?: 0.0
                val xRatio = ((lon - minLon) / (maxLon - minLon)).toFloat().coerceIn(0f, 1f)
                val yRatio = 1f - ((lat - minLat) / (maxLat - minLat)).toFloat().coerceIn(0f, 1f)
                Triple(attraction, xRatio * width, yRatio * height)
            }
        } else {
            // моковые точки, если нет данных/сети
            (0 until 7).map {
                Triple<Attraction?, Float, Float>(
                    null,
                    Random.nextFloat() * width,
                    Random.nextFloat() * height
                )
            }
        }

        points.forEach { (attraction, cx, cy) ->
            paint.color = attraction?.let { categoryColor(it.id) } ?: Color.parseColor("#4E7FFF")
            canvas.drawCircle(cx, cy, max(12f, min(width, height) * 0.03f), paint)
        }
    }

    private fun categoryColor(id: Int): Int {
        val colors = listOf(
            0xFF4E7FFF.toInt(),
            0xFFB45FE6.toInt(),
            0xFF2D9D4A.toInt(),
            0xFF00838F.toInt(),
            0xFFCC7722.toInt(),
            0xFFEF6C00.toInt(),
            0xFF455A64.toInt()
        )
        return colors[id % colors.size]
    }
}

