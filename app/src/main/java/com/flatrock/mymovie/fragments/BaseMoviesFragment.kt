package com.flatrock.mymovie.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flatrock.glib.fragments.BaseFragment
import com.flatrock.networklib.NetworkController
import kotlinx.android.synthetic.main.fragment_movies_list.*

abstract class BaseMoviesFragment: BaseFragment() {
    open fun initPage() {}

    open fun getNextPage() {}

    open fun onRefresh() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movies_recycler.layoutManager = GridLayoutManager(context!!, 2)
        movies_recycler.setOnScrollChangeListener { _, _, _, _, _ ->
            if (!movies_recycler.canScrollVertically(RecyclerView.VERTICAL) && NetworkController.isConnected && !movies_refresh_layout.isRefreshing) {
                getNextPage()
            }
        }
        movies_refresh_layout.setOnRefreshListener {
            if (!NetworkController.isConnected) {
                movies_refresh_layout.isRefreshing = false
                return@setOnRefreshListener
            }
            onRefresh()
        }
    }
}