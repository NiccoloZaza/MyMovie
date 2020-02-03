package com.flatrock.glib.transfermodels

internal class MoviesResponseDTO {
    var page: Int = 0
    var total_results: Int = 0
    var total_pages: Int = 0
    var results: ArrayList<MovieDTO>? = null
}