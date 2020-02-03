package com.flatrock.glib

import android.content.Context
import com.flatrock.glib.abstractions.services.IFavoriteMoviesService
import com.flatrock.glib.abstractions.services.IPopularMoviesService
import com.flatrock.glib.abstractions.services.ITopRatedMoviesService
import com.flatrock.glib.implementations.services.FavoriteMoviesService
import com.flatrock.glib.implementations.services.PopularMoviesService
import com.flatrock.glib.implementations.services.TopRatedMoviesService
import com.flatrock.glib.servicecontainer.ServiceContainer
import com.flatrock.networklib.NetworkController
import io.realm.Realm

object ApplicationInstance {
    internal const val apiKey: String = ""

    fun init(applicationContext: Context) {
        NetworkController.init(applicationContext)
        Realm.init(applicationContext)
        registerServices()
    }

    private fun registerServices() {
        ServiceContainer.registerService(ITopRatedMoviesService::class, TopRatedMoviesService())
        ServiceContainer.registerService(IPopularMoviesService::class, PopularMoviesService())
        ServiceContainer.registerService(IFavoriteMoviesService::class, FavoriteMoviesService())
    }
}