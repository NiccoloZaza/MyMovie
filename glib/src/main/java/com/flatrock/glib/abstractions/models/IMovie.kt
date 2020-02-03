package com.flatrock.glib.abstractions.models

interface IMovie {
    val id: Long
    val coverUrl: String
    val hasVideo: Boolean
    val posterUrl: String
    val forAdults: Boolean
    val movieLanguage: String
    val originalTitle: String
    val title: String
    val overView: String
    val voteAverage: Double
    val releaseDate: String
}