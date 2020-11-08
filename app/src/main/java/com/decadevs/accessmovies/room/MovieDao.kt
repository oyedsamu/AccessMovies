package com.decadevs.accessmovies.room

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Query
import com.decadevs.accessmovies.data.Movie

@Dao
interface MovieDao {
    /** GET ALL MOVIES FROM DATABASE */
    @Query("SELECT * FROM movies_table ORDER BY id ASC")
    fun getAllMovies(): MutableLiveData<List<Movie>>
}