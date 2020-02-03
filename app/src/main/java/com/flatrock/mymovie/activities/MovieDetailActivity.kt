package com.flatrock.mymovie.activities

import android.os.Bundle
import android.view.View
import com.flatrock.glib.abstractions.models.IMovie
import com.flatrock.glib.abstractions.services.IFavoriteMoviesService
import com.flatrock.glib.activities.BaseCompatActivity
import com.flatrock.glib.extensions.ifNotNull
import com.flatrock.glib.extensions.loadImage
import com.flatrock.mymovie.R
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity: BaseCompatActivity() {
    companion object {
        var movie: IMovie? = null
    }

    override val layoutId: Int
        get() = R.layout.activity_movie_detail

    private var isFavorite: Boolean = false

    private var alreadyUpdatingFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cover_image.loadImage(movie?.coverUrl, R.drawable.cover_image_foreground, R.drawable.cover_image_foreground)
        movie_image.loadImage(movie?.posterUrl, R.drawable.poster_placeholder, R.drawable.poster_placeholder)
        movie_title.text = movie?.title
        if (movie?.title != movie?.originalTitle)
            movie_original_title.text = movie?.originalTitle
        movie_overview.text = movie?.overView
        release_date_text.text = movie?.releaseDate
        rating_text.text = movie?.voteAverage?.toString()
        favorite_btn.visibility = View.INVISIBLE

        movie.ifNotNull {
            IFavoriteMoviesService.service?.isFavorite(it)?.onPostExecute { isFavorite ->
                this.isFavorite = isFavorite
                if (isFavorite)
                    favorite_btn.setImageResource(R.drawable.ic_favorite)
                else
                    favorite_btn.setImageResource(R.drawable.ic_not_favorite)
                favorite_btn.visibility = View.VISIBLE
            }?.execute()
        }

        back_btn.setOnClickListener {
            onBackPressed()
        }

        favorite_btn.setOnClickListener {
            if (alreadyUpdatingFavorite)
                return@setOnClickListener
            alreadyUpdatingFavorite = true
            if (isFavorite && movie != null) {
                IFavoriteMoviesService.service?.removeFromFavorites(movie!!)?.onPostExecute {
                    isFavorite = !isFavorite
                    favorite_btn.setImageResource(R.drawable.ic_not_favorite)
                    alreadyUpdatingFavorite = false
                }?.execute()
            } else {
                IFavoriteMoviesService.service?.saveToFavorites(movie!!)?.onPostExecute {
                    isFavorite = !isFavorite
                    favorite_btn.setImageResource(R.drawable.ic_favorite)
                    alreadyUpdatingFavorite = false
                }?.execute()
            }
        }
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}