package com.flatrock.mymovie.fragments

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.flatrock.glib.abstractions.services.ITopRatedMoviesService
import com.flatrock.glib.extensions.ifNotNull
import com.flatrock.mymovie.R
import com.flatrock.mymovie.activities.MovieDetailActivity
import com.flatrock.mymovie.adapters.recycleradapters.MovieRecyclerAdapter
import com.flatrock.networklib.NetworkController
import com.flatrock.networklib.enums.Connectivity
import com.flatrock.networklib.enums.ConnectivityStrength
import com.flatrock.networklib.enums.ConnectivityType
import kotlinx.android.synthetic.main.fragment_movies_list.*

class TopMoviesFragment: BaseMoviesFragment() {
    override val layoutId: Int
        get() = R.layout.fragment_movies_list

    private var updateDataOnInternetConnection: Boolean = false

    private var alreadyGettingNextPage: Boolean = false

    override fun onConnectivityChanged(
        connectivity: Connectivity,
        connectivityStrength: ConnectivityStrength,
        connectivityType: ConnectivityType
    ) {
        super.onConnectivityChanged(connectivity, connectivityStrength, connectivityType)
        if (updateDataOnInternetConnection && connectivity == Connectivity.Connected) {
            if (isVisible)
                getData()
        } else if (!movies_recycler.canScrollVertically(RecyclerView.VERTICAL) && movies_recycler.computeVerticalScrollRange() > 0) {
            getNextPage()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movies_progress_bar.visibility = View.VISIBLE
        no_internet_view.visibility = View.GONE

        if (!NetworkController.isConnected) {
            no_internet_view.visibility = View.VISIBLE
            movies_progress_bar.visibility = View.GONE
            updateDataOnInternetConnection = true
            return
        }

        getData()
    }

    private fun getData() {
        movies_progress_bar.visibility = View.VISIBLE
        no_internet_view.visibility = View.GONE

        ITopRatedMoviesService.service?.getNextPage(false)?.onPostExecute {
            if (it.isEmpty())
                no_data_view.visibility = View.VISIBLE
            val adapter = MovieRecyclerAdapter(it)
            adapter.setOnItemClickListener { movie ->
                MovieDetailActivity.movie = movie
                val intent = Intent(context!!, MovieDetailActivity::class.java)
                startActivity(intent, ActivityOptions.makeCustomAnimation(activity!!, android.R.anim.fade_in, android.R.anim.fade_out).toBundle())
            }
            movies_recycler.adapter = adapter
            movies_progress_bar.visibility = View.GONE
        }?.execute()
    }

    override fun onRefresh() {
        no_data_view.visibility = View.GONE
        ITopRatedMoviesService.service?.getNextPage(true)?.onPostExecute {
            if (it.isEmpty())
                no_data_view.visibility = View.VISIBLE
            movies_recycler.adapter?.ifNotNull { adapter ->
                if (adapter is MovieRecyclerAdapter)
                    adapter.replaceData(it)
            }
            movies_refresh_layout.isRefreshing = false
        }?.execute()
    }

    override fun getNextPage() {
        if (alreadyGettingNextPage)
            return
        alreadyGettingNextPage = true

        next_page_progress.visibility = View.VISIBLE
        ITopRatedMoviesService.service?.getNextPage(false)?.onPostExecute {
            alreadyGettingNextPage = false
            next_page_progress.postDelayed({
                next_page_progress.visibility = View.GONE
                movies_recycler.adapter?.ifNotNull { adapter ->
                    if (adapter is MovieRecyclerAdapter)
                        adapter.replaceData(it)
                }
                movies_refresh_layout.isRefreshing = false
            }, 300)
        }?.onError {
            alreadyGettingNextPage = false
        }?.execute()
    }
}