package com.flatrock.glib.views

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.flatrock.glib.R

class FadingRecyclerView: RecyclerView {
    private var bottomFadeEnabled: Boolean = true

    private var topFadeEnabled: Boolean = true

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        getAttributes(attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        getAttributes(attrs, defStyleAttr)
    }

    init {
        isVerticalFadingEdgeEnabled = true
    }

    private fun getAttributes(attrs: AttributeSet?, defStyleAttr: Int = 0) {
        if (attrs != null) {
            val tArray = context.obtainStyledAttributes(attrs, R.styleable.FadingRecyclerView, defStyleAttr, 0)

            bottomFadeEnabled = tArray.getBoolean(R.styleable.FadingRecyclerView_bottomFadeEnabled, true)
            topFadeEnabled = tArray.getBoolean(R.styleable.FadingRecyclerView_topFadeEnabled, true)

            tArray.recycle()
        }
    }

    override fun getBottomFadingEdgeStrength(): Float {
        if (!bottomFadeEnabled)
            return 0f
        return super.getBottomFadingEdgeStrength()
    }

    override fun getTopFadingEdgeStrength(): Float {
        if (!topFadeEnabled)
            return 0f
        return super.getTopFadingEdgeStrength()
    }
}