package com.example.movieapi.api

import com.example.movieapi.api.models.user.User
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserApi {

    @POST("/api/v1/register")
    suspend fun registerUser(@Body user: User): User

    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun login(
        @Field("grant_type") grant_type :String,
        @Field("username") username :String,
        @Field("password") password :String
    ): User

}