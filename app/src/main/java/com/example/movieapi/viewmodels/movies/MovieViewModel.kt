package com.example.movieapi.viewmodels.movies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapi.api.models.movies.Genres
import com.example.movieapi.api.models.movies.MovieDetailEntity
import com.example.movieapi.api.models.movies.MovieResponse
import com.example.movieapi.repositories.movieRepositories.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MovieViewModel constructor(application: Application) : AndroidViewModel(application) {

    private val movieRepository: MovieRepository = MovieRepository()
    val addMovieListResult: MutableLiveData<Response<MovieResponse>> = MutableLiveData()
    val addMovieFilterListResult: MutableLiveData<Response<MovieResponse>> = MutableLiveData()
    val addMovieDetailResult: MutableLiveData<MovieDetailEntity> = MutableLiveData()
    val genresResult: MutableLiveData<List<Genres>> = MutableLiveData()

    var previousPage:Int = 0
    var currentPage:Int = 1
    var nextPage:Int = 2


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

    fun getMovieById(id:Int) {
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

    fun getMoviesByTitle(title:String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                movieRepository.getMovieByTitle(title).let {
                    addMovieFilterListResult.postValue(it)
                }
            } catch (e: Exception) {
                throw Exception(e.message)
            }
        }
    }

    fun getMoviesByGenre(id:Int,page:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                movieRepository.getMovieListByGenre(id,page).let {
                    addMovieFilterListResult.postValue(it)
                }
            } catch (e: Exception) {
                throw Exception(e.message)
            }
        }
    }

    fun getGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                movieRepository.getGenres().let {
                    genresResult.postValue(it)
                }
            } catch (e: Exception) {
                throw Exception(e.message)
            }
        }
    }

}