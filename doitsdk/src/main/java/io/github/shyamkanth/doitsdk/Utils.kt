package io.github.shyamkanth.doitsdk

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import androidx.core.content.ContextCompat

object Utils {
    fun generateInitialsImage(
        initials: String,
        position: Int,
        diameter: Int = 100
    ): Bitmap {
        val colorArray = listOf(
            Color.parseColor("#E9CAF8"),
            Color.parseColor("#CAE7F8"),
            Color.parseColor("#D0F8CA"),
            Color.parseColor("#F8D2CA"),
            Color.parseColor("#FFD700"),
            Color.parseColor("#FFA07A"),
            Color.parseColor("#98FB98"),
            Color.parseColor("#87CEEB"),
            Color.parseColor("#FF69B4"),
            Color.parseColor("#20B2AA")
        )

        val backgroundColor = colorArray[position % colorArray.size]

        val bitmap = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val paint = Paint().apply {
            isAntiAlias = true
            color = backgroundColor
        }

        val radius = diameter / 2f
        canvas.drawCircle(radius, radius, radius, paint)

        paint.color = Color.BLACK
        paint.textSize = diameter / 2.5f
        paint.typeface = Typeface.DEFAULT_BOLD
        paint.textAlign = Paint.Align.CENTER

        val bounds = Rect()
        paint.getTextBounds(initials, 0, initials.length, bounds)
        val y = radius - (bounds.top + bounds.bottom) / 2f

        canvas.drawText(initials, radius, y, paint)

        return bitmap
    }

}