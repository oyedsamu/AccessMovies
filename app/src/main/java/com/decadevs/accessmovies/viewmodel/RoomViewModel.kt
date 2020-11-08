package com.decadevs.accessmovies.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.decadevs.accessmovies.data.Movie
import com.decadevs.accessmovies.room.MoviesDatabase
import com.decadevs.accessmovies.room.RoomRepository

class RoomViewModel(application: Application): AndroidViewModel(application) {

    private var _allMovies: MutableLiveData<List<Movie>>? = null
    val allMovies: LiveData<List<Movie>>?
        get() = _allMovies

    private val roomRepository: RoomRepository

    /** GET MOVIES FROM DATABASE */
    init {
        val movieDao = MoviesDatabase.getDatabase(application).movieDao()
        roomRepository = RoomRepository(movieDao)
        _allMovies = roomRepository.getAllMovies
    }

}