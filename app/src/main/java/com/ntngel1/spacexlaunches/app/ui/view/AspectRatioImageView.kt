package com.ntngel1.spacexlaunches.app.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import com.ntngel1.spacexlaunches.R

class AspectRatioImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    private var widthMultiplier = 16F
    private var heightMultiplier = 9F

    init {
        attrs?.let { attrs ->
            val styledAttrs =
                context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView)

            val aspectRatioStr = styledAttrs.getString(
                R.styleable.AspectRatioImageView_aspect_ratio
            )

            aspectRatioStr?.split('/')
                ?.takeIf { it.size == 2 }
                ?.forEachIndexed { index, value ->
                    if (index == 0) {
                        widthMultiplier = value.toFloat()
                    } else {
                        heightMultiplier = value.toFloat()
                    }
                }

            styledAttrs.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (widthMeasureSpec == MeasureSpec.UNSPECIFIED && heightMeasureSpec != MeasureSpec.UNSPECIFIED) {
            val width = (measuredHeight / heightMultiplier * widthMultiplier).toInt()
            setMeasuredDimension(width, measuredHeight)
        } else if (heightMeasureSpec == MeasureSpec.UNSPECIFIED && widthMeasureSpec != MeasureSpec.UNSPECIFIED) {
            val height = (measuredWidth / widthMultiplier * heightMultiplier).toInt()
            setMeasuredDimension(measuredWidth, height)
        } else {
            throw IllegalStateException("Cannot measure size of AspectRatioImageView!")
        }
    }

    companion object {
        private const val DEFAULT_ASPECT_RATIO = "16/9"
    }
}