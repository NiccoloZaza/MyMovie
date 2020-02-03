package com.flatrock.glib.abstractions.services

import com.flatrock.glib.abstractions.models.IMovie
import com.flatrock.glib.servicecontainer.Executioner
import com.flatrock.glib.servicecontainer.ServiceContainer

interface ITopRatedMoviesService {
    companion object {
        val service: ITopRatedMoviesService?
            get() {
                return ServiceContainer.getService(ITopRatedMoviesService::class)
            }
    }

    fun getNextPage(refreshed: Boolean): Executioner<ArrayList<IMovie>>
}