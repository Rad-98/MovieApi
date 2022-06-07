package com.example.movieapi.api.models.user

import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("name")
    var name: String? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("updated_at")
    var updatedAt: String? = null,
    @SerializedName("created_at")
    var createdAt: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    var password: String? = null,
    var access_token: String?= null,
    var refresh_token: String?= null
)
