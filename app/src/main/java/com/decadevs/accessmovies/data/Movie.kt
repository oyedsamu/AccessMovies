package com.decadevs.accessmovies.data

import com.google.firebase.database.Exclude

/** The data class for the Movie */
data class Movie (
    @get:Exclude
    var id : String,
    val title : String,
    val movieDescription : String,
    val releaseDate  : String,
    val rating : String,
    val ticketPrice : String,
    val country : String,
    val genre : String,
    val image : String
)

