package com.flatrock.glib.realm.controllers

import com.flatrock.glib.abstractions.models.IMovie
import com.flatrock.glib.extensions.createObjectOrUpdate
import com.flatrock.glib.realm.entities.MovieEntity

internal object RealmFavoriteMoviesController: BaseRealmController() {
    fun saveMovie(movie: IMovie) {
        realm.executeTransaction {
            val savedMovie = realm.createObjectOrUpdate<MovieEntity>(movie.id)
            savedMovie.toEntity(movie)
        }
    }

    fun removeMovie(movie: IMovie) {
        realm.executeTransaction {
            realm.where(MovieEntity::class.java).equalTo("id", movie.id).findAll().deleteAllFromRealm()
        }
    }

    fun getAllMovies(): LinkedHashMap<Long, IMovie> {
        val movieEntities = realm.where(MovieEntity::class.java).findAll()
        val movies = LinkedHashMap<Long, IMovie>()
        movieEntities.forEach { entity ->
            val result = entity.fromEntity()
            movies.put(result.id, result)
        }
        return movies
    }
}