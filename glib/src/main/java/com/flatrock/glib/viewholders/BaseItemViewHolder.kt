package com.flatrock.glib.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.flatrock.glib.annotations.InjectView

abstract class BaseItemViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    internal var clickListener: ((T?) -> Unit)? = null

    open fun bind(data: T?) {
        itemView.setOnClickListener {
            clickListener?.invoke(data)
        }
    }

    init {
        try {
            initializeViews()
        } catch (ignore: Exception) {}
    }

    @Throws(Exception::class)
    private fun initializeViews() {
        val cls = this.javaClass
        val targetFields = cls.declaredFields
        for (i in targetFields.indices) {
            val field = targetFields[i]
            if (field.isAnnotationPresent(InjectView::class.java)) {
                val accessible = field.isAccessible
                field.isAccessible = true
                val annotation = field.getAnnotation(InjectView::class.java)
                val id = (annotation as InjectView).id
                val view = itemView.findViewById<View>(id)
                field.set(this, view as Any)
                field.isAccessible = accessible
            }
        }
    }
}