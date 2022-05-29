package com.example.movieapi.database.models

import com.google.gson.annotations.SerializedName

data class DataM (
    @SerializedName("id")
    val id:Int,
    @SerializedName("title")
    val title:String,
    @SerializedName("poster")
    val poster:String,
    @SerializedName("year")
    val year:String,
    @SerializedName("country")
    val country:String,
    @SerializedName("imdb_rating")
    val imdb_rating:String,
    @SerializedName("genres")
    val genres:List<String>,
    @SerializedName("images")
    val images:List<String>,
        )