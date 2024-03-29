package com.ntngel1.spacexlaunches.app.common.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import com.ntngel1.spacexlaunches.R

class AspectRatioImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    private var widthMultiplier = DEFAULT_WIDTH_MULTIPLIER
    private var heightMultiplier = DEFAULT_HEIGHT_MULTIPLIER

    init {
        attrs?.let { attrs ->
            val styledAttrs =
                context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView)

            styledAttrs.getString(R.styleable.AspectRatioImageView_aspect_ratio)
                ?.let(::parseAspectRatio)

            styledAttrs.recycle()
        }
    }

    private fun parseAspectRatio(aspectRatioStr: String) {
        aspectRatioStr.split('/')
            .takeIf { it.size == 2 }
            ?.forEachIndexed { index, value ->
                if (index == 0) {
                    widthMultiplier = value.toFloat()
                } else {
                    heightMultiplier = value.toFloat()
                }
            }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        when {
            widthMeasureSpec == MeasureSpec.UNSPECIFIED &&
                    heightMeasureSpec != MeasureSpec.UNSPECIFIED -> {
                val width = (measuredHeight / heightMultiplier * widthMultiplier).toInt()
                setMeasuredDimension(width, measuredHeight)
            }

            heightMeasureSpec == MeasureSpec.UNSPECIFIED &&
                    widthMeasureSpec != MeasureSpec.UNSPECIFIED -> {
                val height = (measuredWidth / widthMultiplier * heightMultiplier).toInt()
                setMeasuredDimension(measuredWidth, height)
            }

            else -> throw IllegalStateException("Cannot measure size of AspectRatioImageView!")
        }
    }

    companion object {
        private const val DEFAULT_WIDTH_MULTIPLIER = 1f
        private const val DEFAULT_HEIGHT_MULTIPLIER = 1f
    }
}