package ru.akumakeito.effectivemobile_test.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import ru.akumakeito.effectivemobile_test.R

class DiagonalStrikeTextView @JvmOverloads constructor(
    context : Context,
    attrs : AttributeSet? = null,
    defStyleAttr : Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {
    private val paint = Paint()

    init {
        paint.apply {
            color = context.getColor(R.color.grey)
            strokeWidth = 1f
            isAntiAlias  = true
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawLine(0f, 0.5f, width.toFloat(), height.toFloat(), paint)

    }

}