package com.example.movieapi.viewmodels.users

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movieapi.api.models.user.User
import com.example.movieapi.repositories.userRepositories.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class UserViewModel(application: Application):AndroidViewModel(application) {

    private val repository: UserRepository = UserRepository()
    val registerResult:MutableLiveData<User> = MutableLiveData()


    fun registerUser(user: User){
        viewModelScope.launch {
            try {
                repository.registerUser(user).let {
                    registerResult.postValue(it)
                }
            }catch (e:Exception){
                Log.i("REG",e.message.toString())
            }

        }
    }

    fun login(grant_type:String,username:String,password:String){
        viewModelScope.launch {
            try {
                repository.login(grant_type,username,password).let {
                    registerResult.postValue(it)
                }
            }catch (e:Exception){
                Log.i("REG",e.message.toString())
            }

        }
    }





}