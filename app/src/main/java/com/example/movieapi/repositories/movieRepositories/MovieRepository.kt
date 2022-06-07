package com.example.movieapi.repositories.movieRepositories

import com.example.movieapi.api.models.movies.Genres
import com.example.movieapi.api.models.movies.MovieDetailEntity
import com.example.movieapi.api.models.movies.MovieResponse
import com.example.movieapi.repositories.BaseRepository
import retrofit2.Response


class MovieRepository(): BaseRepository() {

    suspend fun getMovies(page:String): Response<MovieResponse> {
        return moviesApi.getMovieList(page)
    }

    suspend fun getMovieById(id:Int): MovieDetailEntity {
        return moviesApi.getMovieListById(id)
    }

    suspend fun getMovieByTitle(title:String): Response<MovieResponse> {
        return moviesApi.getMovieListByTitle(title)
    }

    suspend fun getMovieListByGenre(id:Int,page:Int): Response<MovieResponse> {
        return moviesApi.getMovieListByGenre(id,page)
    }


    suspend fun getGenres(): List<Genres> {
        return moviesApi.getGenres()
    }

}