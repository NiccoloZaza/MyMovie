package com.flatrock.mymovie.fragments

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.flatrock.glib.abstractions.services.IFavoriteMoviesService
import com.flatrock.mymovie.R
import com.flatrock.mymovie.activities.MovieDetailActivity
import com.flatrock.mymovie.adapters.recycleradapters.MovieRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_movies_list.*

class FavoriteMoviesFragment: BaseMoviesFragment() {
    override val layoutId: Int
        get() = R.layout.fragment_movies_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movies_refresh_layout.isEnabled = false
    }

    override fun onResume() {
        super.onResume()
        movies_progress_bar.visibility = View.VISIBLE
        IFavoriteMoviesService.service?.getFavoriteMovies()?.onPostExecute {
            if (movies_recycler.adapter != null) {
                (movies_recycler.adapter as MovieRecyclerAdapter).replaceData(it)
            } else {
                val adapter = MovieRecyclerAdapter(it)
                movies_recycler.adapter = adapter
                adapter.setOnItemClickListener { movie ->
                    MovieDetailActivity.movie = movie
                    val intent = Intent(context!!, MovieDetailActivity::class.java)
                    startActivity(intent, ActivityOptions.makeCustomAnimation(activity!!, android.R.anim.fade_in, android.R.anim.fade_out).toBundle())
                }
            }
            if (it.isEmpty()) {
                no_data_view.visibility = View.VISIBLE
            } else
                no_data_view.visibility = View.GONE
            movies_progress_bar.visibility = View.GONE
        }?.execute()
    }
}