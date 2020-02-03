package com.flatrock.glib.transfermodels

import com.flatrock.glib.abstractions.models.IMovie
import com.flatrock.glib.implementations.models.Movie

internal class MovieDTO: BaseDTO<IMovie>() {
    override fun toModel(): IMovie {
        return Movie(
            id,
            video,
            "https://image.tmdb.org/t/p/w500$poster_path",
            "https://image.tmdb.org/t/p/w500$backdrop_path",
            adult,
            original_language,
            original_title,
            title,
            overview,
            vote_average,
            release_date
        )
    }

    var popularity: Double = 0.0
    var vote_count: Int = 0
    var video: Boolean = false
    var poster_path: String = ""
    var adult: Boolean = false
    var backdrop_path: String = ""
    var original_language: String = ""
    var original_title: String = ""
    var title: String = ""
    var vote_average: Double = 0.0
    var overview: String = ""
    var release_date: String = ""
}