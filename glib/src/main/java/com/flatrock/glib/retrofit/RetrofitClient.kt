@file:Suppress("unused")

package com.flatrock.glib.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object RetrofitClient {
    private const val baseUrl: String = "https://api.themoviedb.org/3/"

    private var retrofit: Retrofit? = null

    internal fun getClient(): Retrofit {
        if (retrofit == null) {
            val gson: Gson = GsonBuilder()
                .setLenient()
                .create()

            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(OkHttpClient.Builder().build())
                .build()
        }
        return retrofit!!
    }

    internal inline fun <reified T> getRetrofit(): T {
        return getClient().create(T::class.java)
    }
}