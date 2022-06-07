package com.example.movieapi.repositories.userRepositories

import com.example.movieapi.api.models.user.User
import com.example.movieapi.repositories.BaseRepository
import retrofit2.Response

class UserRepository : BaseRepository() {

    suspend fun registerUser(userJson: User):User{
        return userApi.registerUser(userJson)
    }

    suspend fun login(grant_type:String,username:String,password:String):User{
        return userApi.login(grant_type,username,password)
    }




}