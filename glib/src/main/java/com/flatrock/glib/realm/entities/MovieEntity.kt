package com.flatrock.glib.realm.entities

import com.flatrock.glib.abstractions.models.IMovie
import com.flatrock.glib.implementations.models.Movie
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

internal open class MovieEntity(@PrimaryKey var id: Long = 0) : RealmObject() {
    var hasVideo: Boolean? = null
    var posterUrl: String? = null
    var coverUrl: String? = null
    var forAdults: Boolean? = null
    var movieLanguage: String? = null
    var originalTitle: String? = null
    var title: String? = null
    var overView: String? = null
    var voteAverage: Double? = null
    var releaseDate: String? = null

    fun toEntity(movie: IMovie) {
        hasVideo = movie.hasVideo
        posterUrl = movie.posterUrl
        coverUrl = movie.coverUrl
        forAdults = movie.forAdults
        movieLanguage = movie.movieLanguage
        originalTitle = movie.originalTitle
        title = movie.title
        overView = movie.overView
        voteAverage = movie.voteAverage
        releaseDate = movie.releaseDate
    }

    fun fromEntity(): IMovie {
        return Movie(
            id,
            hasVideo ?: false,
            posterUrl ?: "",
            coverUrl ?: "",
            forAdults ?: false,
            movieLanguage ?: "",
            originalTitle ?: "",
            title ?: "",
            overView ?: "",
            voteAverage ?: 0.0,
            releaseDate ?: ""
        )
    }
}