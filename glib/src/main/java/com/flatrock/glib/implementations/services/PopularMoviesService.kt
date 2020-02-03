package com.flatrock.glib.implementations.services

import com.flatrock.glib.abstractions.models.IMovie
import com.flatrock.glib.abstractions.services.IPopularMoviesService
import com.flatrock.glib.extensions.ifNotNull
import com.flatrock.glib.retrofit.abstractions.MovieServices
import com.flatrock.glib.servicecontainer.Executioner
import com.flatrock.glib.transfermodels.MoviesResponseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularMoviesService: IPopularMoviesService {
    var currentPage: Int = 1
    val uniqueMovies: LinkedHashMap<Long, IMovie> = LinkedHashMap()

    override fun getNextPage(refreshed: Boolean): Executioner<ArrayList<IMovie>> {
        val executioner = Executioner<ArrayList<IMovie>>()

        executioner.setServiceExecutioner {
            if (refreshed) {
                currentPage = 1
                uniqueMovies.clear()
            }
            MovieServices.retrofit.getPopularMovies(currentPage).enqueue(object: Callback<MoviesResponseDTO> {
                override fun onFailure(call: Call<MoviesResponseDTO>, t: Throwable) {
                    executioner.postExecute(ArrayList(uniqueMovies.values.toList()))
                }

                override fun onResponse(
                    call: Call<MoviesResponseDTO>,
                    response: Response<MoviesResponseDTO>
                ) {
                    if (response.code() == 200 && response.body() != null) {
                        response.body()?.results?.ifNotNull {
                            currentPage++
                            for (movie in it) {
                                uniqueMovies[movie.id] = movie.toModel()
                            }
                            executioner.postExecute(ArrayList(uniqueMovies.values.toList()))
                        }
                    } else {
                        executioner.postExecute(ArrayList(uniqueMovies.values.toList()))
                    }
                }
            })
        }

        return executioner
    }
}