package com.decadevs.accessmovies.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.decadevs.accessmovies.data.Comment
import com.decadevs.accessmovies.data.Movie
import com.decadevs.accessmovies.room.RMDatabase
import com.decadevs.accessmovies.room.RoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomViewModel(application: Application): AndroidViewModel(application) {

    private var _allMovies: MutableLiveData<List<Movie>>? = null
    val allMovies: LiveData<List<Movie>>?
        get() = _allMovies

    private var _allComments: MutableLiveData<List<Comment>>? = null
    val allComments: LiveData<List<Comment>>?
        get() = _allComments

    private val roomRepository: RoomRepository

    init {
        /** INITIALISE ROOM DATABASE */
        val movieDao = RMDatabase.getDatabase(application).movieDao()
        val commentDao = RMDatabase.getDatabase(application).commentDao()
        roomRepository = RoomRepository(movieDao, commentDao)

        /** GET MOVIES FROM DATABASE */
        _allMovies = roomRepository.getAllMovies

        /** GET COMMENT FROM DATABASE */
        _allComments = roomRepository.getAllComments
    }

    /** ADD MOVIE TO DATABASE */
    fun addMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.addMovie(movie)
        }
    }

    /** ADD COMMENT TO DATABASE */
    fun addComment(comment: Comment) {
        viewModelScope.launch(Dispatchers.IO) {
            roomRepository.addComment(comment)
        }
    }

}