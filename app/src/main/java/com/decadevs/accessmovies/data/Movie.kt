package com.decadevs.accessmovies.data

data class Movie (

    val id : String,
    val name : String,
    val description : String,
    val releaseDate  : String,
    val rating : String,
    val ticketPrice : String,
    val country : String,
    val genre : String,
    val photo : Int,
//    val comments: List<Comment> = arrayListOf()
)

data class Comment (
    val id: String,
    val name: String,
    val comment: String,
    val movieId: String
)

