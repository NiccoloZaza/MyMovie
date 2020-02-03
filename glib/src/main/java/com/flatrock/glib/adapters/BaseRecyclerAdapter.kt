@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package com.flatrock.glib.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.flatrock.glib.viewholders.BaseItemViewHolder

abstract class BaseRecyclerAdapter<T, Holder : BaseItemViewHolder<T>>(data: ArrayList<T>) :
    RecyclerView.Adapter<Holder>() {

    private var data: ArrayList<T>?

    private var onClickListener: ((item: T?) -> Unit)? = null

    init {
        this.data = data
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    protected fun getView(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater
            .from(parent.context)
            .inflate(getItemResourceLayout(viewType), parent, false)
    }

    @LayoutRes
    protected abstract fun getItemResourceLayout(viewType: Int): Int

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.clickListener = onClickListener
        holder.bind(data?.get(position))
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun add(item: T) {
        data?.add(item)
        notifyItemInserted(data?.size!! - 1)
    }

    fun addAll(items: List<T>) {
        add(items)
    }

    fun add(item: T, position: Int) {
        data?.add(position, item)
        notifyItemInserted(position)
    }

    fun add(items: List<T>) {
        val size = items.size
        for (i in 0 until size) {
            data?.add(items[i])
        }
        notifyDataSetChanged()
    }

    fun addOrUpdate(item: T) {
        val i = data?.indexOf(item)
        if (i!! >= 0) {
            data?.set(i, item)
            notifyItemChanged(i)
        } else {
            add(item)
        }
    }

    fun addOrUpdate(items: List<T>) {
        val size = items.size
        for (i in 0 until size) {
            val item = items[i]
            val x = data?.indexOf(item)
            if (x!! >= 0) {
                data?.set(x, item)
            } else {
                add(item)
            }
        }
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        if (position >= 0 && position < data?.size!!) {
            data?.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun remove(item: T) {
        val position = data?.indexOf(item)
        remove(position!!)
    }

    fun replaceData(items: ArrayList<T>?) {
        data = items
        notifyDataSetChanged()
    }

    fun clear() {
        data?.clear()
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(callBack: ((item: T?) -> Unit)?) {
        onClickListener = callBack
    }
}