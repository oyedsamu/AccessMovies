package com.decadevs.accessmovies.room

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decadevs.accessmovies.data.Movie

@Dao
interface MovieDao {
    /** GET ALL MOVIES FROM DATABASE */
    @Query("SELECT * FROM movies_table ORDER BY id ASC")
    fun getAllMovies(): MutableLiveData<List<Movie>>

    /** ADD MOVIE TO DATABASE */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movie: Movie)
}