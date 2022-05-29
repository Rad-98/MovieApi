package com.example.movieapi.database.models

import com.google.gson.annotations.SerializedName

data class MetaData(

    @SerializedName("current_page")
    var current_page: String,
    @SerializedName("per_page")
    var per_page: Long,
    @SerializedName("page_count")
    var page_count: Long,
    @SerializedName("total_count")
    var total_count: Long
)