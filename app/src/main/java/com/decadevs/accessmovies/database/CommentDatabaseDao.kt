package com.decadevs.accessmovies.database

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface CommentDatabaseDao {

    // insert comment
    @Insert
    fun insertComment ()
}