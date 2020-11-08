package com.decadevs.accessmovies.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments_table")
data class Comment (
    @PrimaryKey(autoGenerate = true)
    var id: String,
    var movieId: String,
    val user: String,
    val comment: String
)