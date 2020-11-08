package com.decadevs.accessmovies.data

data class Comment (
    var id: String,
    var movieId: String,
    val user: String,
    val comment: String
)