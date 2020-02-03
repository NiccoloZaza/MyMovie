package com.flatrock.glib.abstractions.services

import com.flatrock.glib.abstractions.models.IMovie
import com.flatrock.glib.servicecontainer.Executioner
import com.flatrock.glib.servicecontainer.ServiceContainer
import com.flatrock.glib.servicecontainer.VoidExecutioner

interface IFavoriteMoviesService {
    companion object {
        val service: IFavoriteMoviesService?
            get() {
                return ServiceContainer.getService(IFavoriteMoviesService::class)
            }
    }

    fun getFavoriteMovies(): Executioner<ArrayList<IMovie>>
    fun saveToFavorites(movie: IMovie): VoidExecutioner
    fun removeFromFavorites(movie: IMovie): VoidExecutioner
    fun isFavorite(movie: IMovie): Executioner<Boolean>
}