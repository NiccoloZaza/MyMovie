@file:Suppress("unused")

package com.flatrock.glib.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import com.flatrock.glib.R

class SelectableItem<T> (var identification: T, var imageResource: Int)

class FancySelector<T> : FrameLayout {
    //region Companion
    companion object {
        const val HORIZONTAL : Int = 0
        const val VERTICAL : Int = 1
    }
    //endregion

    //region Fields
    private var itemSize: Int = -1
    private var cornerRadius: Int = 0
    private var imagePaddings: Int = 5
    private var selectedItemBackgroundColor: Int = Color.YELLOW
    private var selectedItemImageColor: Int = Color.WHITE
    private var defaultBackgroundColor: Int = Color.GRAY
    private var defaultImageColor: Int = Color.BLACK
    private var animationDuration: Int = 200
    private var orientation: Int = HORIZONTAL

    private lateinit var line: LinearLayout
    private lateinit var selectedBackground: FrameLayout
    private lateinit var currentSelectedView: ImageView

    private var onItemSelectListener: ((selectedItem: T) -> Unit)? = null
    //endregion

    //region Properties
    val selectedIdentifier: T
        get() {
            return currentSelectedView.tag as T
        }
    //endregion

    //region Constructors
    constructor(context: Context) : super(context) {
        initClass()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initClass(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr : Int) : super(context, attrs, defStyleAttr) {
        initClass(attrs, defStyleAttr)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        initClass(attrs, defStyleAttr, defStyleRes)
    }
    //endregion

    //region Custom Methods
    fun <F>getGenericViewInstance() : FancySelector<F> {
        return this as FancySelector<F>
    }

    fun setOnItemSelectListener(onItemSelectListener: ((selectedItem: T) -> Unit)?) {
        this.onItemSelectListener = onItemSelectListener
    }

    private fun getAttributes(attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) {
        if (attrs == null)
            return
        val tArray = context.obtainStyledAttributes(attrs, R.styleable.FancySelector, defStyleAttr, defStyleRes)
        itemSize = tArray.getDimensionPixelSize(R.styleable.FancySelector_itemSize, itemSize)
        selectedItemBackgroundColor = tArray.getColor(R.styleable.FancySelector_selectedItemBackgroundColor, selectedItemBackgroundColor)
        selectedItemImageColor = tArray.getColor(R.styleable.FancySelector_selectedItemImageColor, selectedItemImageColor)
        defaultBackgroundColor = tArray.getColor(R.styleable.FancySelector_defaultBackgroundColor, defaultBackgroundColor)
        defaultImageColor = tArray.getColor(R.styleable.FancySelector_defaultImageColor, defaultImageColor)
        cornerRadius = tArray.getDimensionPixelSize(R.styleable.FancySelector_cornerRadius, cornerRadius)
        animationDuration = tArray.getInteger(R.styleable.FancySelector_animationDuration, animationDuration)
        imagePaddings = tArray.getDimensionPixelSize(R.styleable.FancySelector_imagePaddings, imagePaddings)
        orientation = tArray.getInteger(R.styleable.FancySelector_orientation, orientation)
        tArray.recycle()
    }

    private fun initClass(attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) {
        if (attrs != null)
            getAttributes(attrs, defStyleAttr, defStyleRes)
        initView()
        initLine()
    }

    private fun initLine() {
        line = LinearLayout(context)
        line.orientation =
            if (orientation == 0) LinearLayout.HORIZONTAL
            else LinearLayout.VERTICAL
        line.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        this.addView(line)
    }

    private fun initView() {
        background = getBackgroundDrawable()
        selectedBackground = FrameLayout(context)
        selectedBackground.background = getSelectedBackgroundDrawable()
        addView(selectedBackground)
    }

    private fun getBackgroundDrawable(): Drawable {
        val backDrawable = GradientDrawable()
        backDrawable.shape = GradientDrawable.RECTANGLE
        backDrawable.setColor(defaultBackgroundColor)
        backDrawable.cornerRadius = cornerRadius.toFloat()
        return backDrawable
    }

    private fun getSelectedBackgroundDrawable(): Drawable {
        val backDrawable = GradientDrawable()
        backDrawable.shape = GradientDrawable.RECTANGLE
        backDrawable.setColor(selectedItemBackgroundColor)
        backDrawable.cornerRadius = cornerRadius.toFloat()
        return backDrawable
    }

    fun addItems(items: ArrayList<Any>, defaultSelectedPosition: Int) {
        var selectedPosition = defaultSelectedPosition
        if (selectedPosition >= items.size)
            selectedPosition = items.size - 1
        else if (selectedPosition < 0)
            selectedPosition = 0

        items.forEachIndexed { index, selectableItem ->
            addItem(selectableItem as SelectableItem<T>, index == selectedPosition)
        }
    }

    private fun addItem(item: SelectableItem<T>, selected: Boolean) {
        val image = ImageView(context)
        image.setImageResource(item.imageResource)
        image.setPadding(imagePaddings, imagePaddings, imagePaddings, imagePaddings)
        if (orientation == HORIZONTAL)
            image.layoutParams = LinearLayout.LayoutParams(itemSize, itemSize)
        else
            image.layoutParams = LinearLayout.LayoutParams(itemSize, itemSize)
        image.tag = item.identification
        image.setOnClickListener {
            if (currentSelectedView == it)
                return@setOnClickListener
            if (onItemSelectListener != null) {
                onItemSelectListener?.invoke(it.tag as T)
            }
            updateSelection(it as ImageView)
        }
        line.addView(image)
        if (selected) {
            currentSelectedView = image
            image.setColorFilter(selectedItemImageColor)
            selectedBackground.post{
                selectedBackground.layoutParams = LayoutParams(image.width, image.height)
                selectedBackground.y = image.y
                selectedBackground.x = image.x
            }
        } else {
            image.setColorFilter(defaultImageColor)
        }
    }

    private fun updateSelection(selectedView: ImageView) {
        currentSelectedView.setColorFilter(defaultImageColor)
        currentSelectedView = selectedView
        currentSelectedView.setColorFilter(selectedItemImageColor)

        val startPosition =
            if (orientation == HORIZONTAL)
                selectedBackground.x
            else
                selectedBackground.y

        val endPosition =
            if (orientation == HORIZONTAL)
                selectedView.x
            else
                selectedView.y

        val animation = ValueAnimator.ofFloat(startPosition, endPosition)
        animation.duration = animationDuration.toLong()

        animation.addUpdateListener {
            val value = it.animatedValue as Float
            if (orientation == HORIZONTAL)
                selectedBackground.x = value
            else
                selectedBackground.y = value
        }
        animation.start()
    }
    //endregion
}