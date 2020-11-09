package com.decadevs.accessmovies.data

import com.google.firebase.database.Exclude

/** Comment Data Class */
data class Comment(
    @get:Exclude
    var id: String,
    var movieId: String,
    val user: String,
    val comment: String
)