package com.example.movieapi.database.api

import com.example.movieapi.database.models.MovieDetailEntity
import com.example.movieapi.database.models.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("api/v1/movies")
    suspend fun getMovieList(@Query("page") page:String):Response<MovieResponse>

    @GET("api/v1/movies")
    suspend fun getMovieListByTitle(@Query("page") title:String,@Query("page") page:Int):Response<MovieResponse>

    @GET("/api/v1/movies/{id}")
    suspend fun getMovieListById(@Path("id") id:Long):MovieDetailEntity
}
