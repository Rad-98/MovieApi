package com.example.movieapi.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapi.database.models.MovieDetailEntity
import com.example.movieapi.database.models.MovieResponse
import com.example.movieapi.repositories.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MovieViewModel constructor(application: Application) : AndroidViewModel(application) {

    private val movieRepository:MovieRepository = MovieRepository()
    val addMovieListResult: MutableLiveData<Response<MovieResponse>> = MutableLiveData()
    val addMovieDetailResult: MutableLiveData<MovieDetailEntity> = MutableLiveData()

    fun getMovies(page:String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                movieRepository.getMovies(page).let {
                    addMovieListResult.postValue(it)
                }
            } catch (e: Exception) {
                throw Exception(e.message)
            }
        }
    }

    fun getMovieById(id:Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                movieRepository.getMovieById(id).let {
                    addMovieDetailResult.postValue(it)
                }
            } catch (e: Exception) {
                throw Exception(e.message)
            }
        }
    }


}