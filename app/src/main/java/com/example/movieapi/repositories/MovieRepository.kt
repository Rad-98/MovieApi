package com.example.movieapi.repositories

import com.example.movieapi.database.models.MovieDetailEntity
import com.example.movieapi.database.models.MovieResponse
import retrofit2.Response

class MovieRepository():BaseRepository() {

    suspend fun getMovies(page:String): Response<MovieResponse> {
        return moviesApi.getMovieList(page)
    }

    suspend fun getMovieById(id:Long): MovieDetailEntity {
        return moviesApi.getMovieListById(id)
    }

}