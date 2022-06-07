package com.example.movieapi.api


import com.example.movieapi.api.models.movies.Genres
import com.example.movieapi.api.models.movies.MovieDetailEntity
import com.example.movieapi.api.models.movies.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("api/v1/movies")
    suspend fun getMovieList(@Query("page") page: String): Response<MovieResponse>

    @GET("api/v1/movies")
    suspend fun getMovieListByTitle(@Query("q") title: String): Response<MovieResponse>

    @GET("/api/v1/movies/{id}")
    suspend fun getMovieListById(@Path("id") id: Int): MovieDetailEntity

    @GET("api/v1/genres")
    suspend fun getGenres(): List<Genres>

    @GET("/api/v1/genres/{genre_id}/movies")
    suspend fun getMovieListByGenre(
        @Path("genre_id") id: Int,
        @Query("page") page: Int
    ): Response<MovieResponse>
}
