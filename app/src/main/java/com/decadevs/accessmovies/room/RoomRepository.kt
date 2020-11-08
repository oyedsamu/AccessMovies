package com.decadevs.accessmovies.room

import androidx.lifecycle.MutableLiveData
import com.decadevs.accessmovies.data.Movie

class RoomRepository(private val movieDao: MovieDao) {

    /** GET ALL MOVIES IN DATABASE */
    val getAllMovies: MutableLiveData<List<Movie>> = movieDao.getAllMovies()

}