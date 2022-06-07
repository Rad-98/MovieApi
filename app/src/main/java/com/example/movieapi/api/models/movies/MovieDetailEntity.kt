package com.example.movieapi.api.models.movies

import com.google.gson.annotations.SerializedName

data class MovieDetailEntity(
    var id: Long,
    var title: String,
    var poster: String,
    var year: String,
    var rated: String,
    var released: String,
    var runtime: String,
    var director: String,
    var writer: String,
    var actors: String,
    var plot: String,
    var country: String,
    var awards: String,
    var metascore: String,

    @SerializedName("imdb_rating")
    var imdbRating: String,

    @SerializedName("imdb_votes")
    var imdbVotes: String,

    @SerializedName("imdb_id")
    var imdbID: String,

    var type: String,
    var genres: List<String>,
    var images: ArrayList<String>
)
