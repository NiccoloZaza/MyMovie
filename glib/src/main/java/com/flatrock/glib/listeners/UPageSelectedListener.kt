package com.flatrock.glib.listeners

import androidx.viewpager.widget.ViewPager

class UPageSelectedListener: ViewPager.OnPageChangeListener {
    private var _onItemSelected: ((position: Int) -> Unit)? = null

    @Suppress("unused")
    fun onPageSelected(func: (position: Int) -> Unit) {
        _onItemSelected = func
    }

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        _onItemSelected?.invoke(position)
    }
}