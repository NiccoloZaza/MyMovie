package com.flatrock.glib.implementations.models

import com.flatrock.glib.abstractions.models.IMovie

class Movie(
    override val id: Long,
    override val hasVideo: Boolean,
    override val posterUrl: String,
    override val coverUrl: String,
    override val forAdults: Boolean,
    override val movieLanguage: String,
    override val originalTitle: String,
    override val title: String,
    override val overView: String,
    override val voteAverage: Double,
    override val releaseDate: String
) : IMovie