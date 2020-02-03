package com.flatrock.glib.abstractions.services

import com.flatrock.glib.abstractions.models.IMovie
import com.flatrock.glib.servicecontainer.Executioner
import com.flatrock.glib.servicecontainer.ServiceContainer

interface IPopularMoviesService {
    companion object {
        val service: IPopularMoviesService?
            get() {
                return ServiceContainer.getService(IPopularMoviesService::class)
            }
    }

    fun getNextPage(refreshed: Boolean): Executioner<ArrayList<IMovie>>
}