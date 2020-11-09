package com.decadevs.accessmovies.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.Exclude

@Entity(tableName = "movies_table")
data class MovieR (
    @PrimaryKey(autoGenerate = true)
    @get:Exclude
    var id : Int,
    val name : String,
    val description : String,
    val releaseDate  : String,
    val rating : String,
    val ticketPrice : String,
    val country : String,
    val genre : String,
    val photo : Int

)