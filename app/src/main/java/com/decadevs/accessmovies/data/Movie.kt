package com.decadevs.accessmovies.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.Exclude

data class Movie (
    @get:Exclude
    var id : String,
    val name : String,
    val description : String,
    val releaseDate  : String,
    val rating : String,
    val ticketPrice : String,
    val country : String,
    val genre : String,
    val photo : Int,

)