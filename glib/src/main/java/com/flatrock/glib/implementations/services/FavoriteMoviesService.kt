package com.flatrock.glib.implementations.services

import com.flatrock.glib.abstractions.models.IMovie
import com.flatrock.glib.abstractions.services.IFavoriteMoviesService
import com.flatrock.glib.realm.controllers.RealmFavoriteMoviesController
import com.flatrock.glib.servicecontainer.Executioner
import com.flatrock.glib.servicecontainer.VoidExecutioner

class FavoriteMoviesService: IFavoriteMoviesService {
    private var favoriteMovies: ArrayList<IMovie>? = null

    override fun getFavoriteMovies(): Executioner<ArrayList<IMovie>> {
        val executioner = Executioner<ArrayList<IMovie>>()

        executioner.setServiceExecutioner {
            if (favoriteMovies == null && favoriteMovies != null) {
                executioner.postExecute(favoriteMovies!!)
            } else {
                favoriteMovies = ArrayList(RealmFavoriteMoviesController.getAllMovies().values)
                if (favoriteMovies == null)
                    executioner.postExecute(ArrayList())
                else
                    executioner.postExecute(favoriteMovies!!)
            }
        }
        return executioner
    }

    override fun saveToFavorites(movie: IMovie): VoidExecutioner {
        val executioner = VoidExecutioner()

        executioner.setServiceExecutioner {
            favoriteMovies?.add(movie)
            RealmFavoriteMoviesController.saveMovie(movie)
            executioner.postExecute()
        }

        return executioner
    }

    override fun removeFromFavorites(movie: IMovie): VoidExecutioner {
        val executioner = VoidExecutioner()

        executioner.setServiceExecutioner {
            favoriteMovies?.removeIf {
                it.id == movie.id
            }
            RealmFavoriteMoviesController.removeMovie(movie)
            executioner.postExecute()
        }

        return executioner
    }

    override fun isFavorite(movie: IMovie): Executioner<Boolean> {
        val executioner = Executioner<Boolean>()

        executioner.setServiceExecutioner {
            getFavoriteMovies().onPostExecute {
                val result = it.filter { x ->
                    x.id == movie.id
                }
                executioner.postExecute(result.isNotEmpty())
            }?.execute()
        }

        return executioner
    }
}