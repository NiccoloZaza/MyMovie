package com.flatrock.glib.retrofit.abstractions

import com.flatrock.glib.retrofit.RetrofitClient

internal interface GenreServices {
    companion object {
        val retrofit: MovieServices
            get() = RetrofitClient.getRetrofit()
    }
}