package com.example.movieapi.repositories

import com.example.movieapi.database.RetrofitHelper
import com.example.movieapi.database.api.MoviesApi

open class BaseRepository {
    protected var moviesApi:MoviesApi =RetrofitHelper.getInstance().create(MoviesApi::class.java)
}