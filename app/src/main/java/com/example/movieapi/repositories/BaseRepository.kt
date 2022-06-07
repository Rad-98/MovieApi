package com.example.movieapi.repositories

import com.example.movieapi.MainApplication.Companion.appContext
import com.example.movieapi.api.RetrofitHelper
import com.example.movieapi.api.MoviesApi
import com.example.movieapi.api.UserApi
import com.example.movieapi.database.BookmarkDataBase

open class BaseRepository {
    protected var moviesApi: MoviesApi =RetrofitHelper.getInstance().create(MoviesApi::class.java)
    protected var bookmarkDb: BookmarkDataBase =BookmarkDataBase.getInstance(appContext)
    protected var userApi: UserApi =RetrofitHelper.getInstance().create(UserApi::class.java)
}