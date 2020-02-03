package com.flatrock.mymovie.adapters.recycleradapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.flatrock.glib.abstractions.models.IMovie
import com.flatrock.glib.adapters.BaseRecyclerAdapter
import com.flatrock.glib.annotations.InjectView
import com.flatrock.glib.extensions.ifNotNull
import com.flatrock.glib.extensions.loadImage
import com.flatrock.glib.viewholders.BaseItemViewHolder
import com.flatrock.mymovie.R

class MovieRecyclerAdapter(data: ArrayList<IMovie>): BaseRecyclerAdapter<IMovie, BaseItemViewHolder<IMovie>>(data) {
    override fun getItemResourceLayout(viewType: Int): Int {
        return R.layout.item_movie
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemViewHolder<IMovie> {
        return MovieViewHolder(getView(parent, viewType))
    }
}

class MovieViewHolder(itemView: View): BaseItemViewHolder<IMovie>(itemView) {
    @InjectView(id = R.id.movie_image)
    private lateinit var moviePoster: ImageView

    @InjectView(id = R.id.rating_text)
    private lateinit var rating: TextView

    override fun bind(data: IMovie?) {
        super.bind(data)
        data?.ifNotNull {
            rating.text = it.voteAverage.toString()
            moviePoster.loadImage(it.posterUrl, R.drawable.poster_placeholder, R.drawable.poster_placeholder)
        }
    }
}