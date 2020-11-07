package com.decadevs.accessmovies.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_comment")
data class Comment (
        @PrimaryKey(autoGenerate = true)
        var movieId : Long = 0L,
        @ColumnInfo(name = "start_time_milli")
        var commentMessage : String
)