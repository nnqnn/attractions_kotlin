package com.nnqnn.attractions.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.nnqnn.attractions.model.Attraction
import com.nnqnn.attractions.model.MapBounds
import kotlin.math.max
import kotlin.math.min

class MapOverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val data = mutableListOf<Pair<Attraction, Pair<Double, Double>>>()
    private var bounds: MapBounds? = null
    var onSelect: ((Attraction) -> Unit)? = null

    fun setData(attractions: List<Attraction>, mapBounds: MapBounds) {
        data.clear()
        data.addAll(attractions.map { it to it.coords })
        bounds = mapBounds
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val b = bounds ?: return
        if (data.isEmpty()) return
        val minLon = b.minLon
        val maxLon = b.maxLon
        val minLat = b.minLat
        val maxLat = b.maxLat
        data.forEach { (attraction, coords) ->
            val xRatio = ((coords.second - minLon) / (maxLon - minLon)).toFloat().coerceIn(0f, 1f)
            val yRatio = 1f - ((coords.first - minLat) / (maxLat - minLat)).toFloat().coerceIn(0f, 1f)
            val cx = xRatio * width
            val cy = yRatio * height
            paint.color = categoryColor(attraction.id)
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

