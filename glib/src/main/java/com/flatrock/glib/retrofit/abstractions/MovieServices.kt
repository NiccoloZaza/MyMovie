package com.flatrock.glib.retrofit.abstractions

import com.flatrock.glib.ApplicationInstance
import com.flatrock.glib.retrofit.RetrofitClient
import com.flatrock.glib.transfermodels.MoviesResponseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface MovieServices {
    companion object {
        val retrofit: MovieServices
            get() = RetrofitClient.getRetrofit()
    }

    @GET("movie/popular?api_key=${ApplicationInstance.apiKey}")
    fun getPopularMovies(@Query("page") page: Int): Call<MoviesResponseDTO>

    @GET("movie/top_rated?api_key=${ApplicationInstance.apiKey}")
    fun getTopRatedMovies(@Query("page") page: Int): Call<MoviesResponseDTO>
}