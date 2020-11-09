package com.decadevs.accessmovies.data

import com.google.firebase.database.Exclude

data class Comment (
    @get:Exclude
    var id: String,
    var movieId: String,
    val user: String,
    val comment: String
)