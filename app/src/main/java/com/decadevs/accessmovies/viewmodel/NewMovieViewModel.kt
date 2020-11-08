package com.decadevs.accessmovies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.decadevs.accessmovies.data.Movie
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class NewMovieViewModel(): ViewModel() {
    val moviesDatabase = FirebaseDatabase.getInstance().getReference()

    /** LIVEDATA TO HOLD ALL MOVIES */
    private val _newMovieResult: MutableLiveData<Exception>? = null
    val newMoviewResult: LiveData<Exception>?
        get() = _newMovieResult

    fun addNewMovie(movie: Movie) {
        /** ADD NEW MOVIE */
        movie.id = moviesDatabase.push().key.toString()
        moviesDatabase.child(movie.id!!).setValue(movie)
            .addOnCompleteListener{
                /** HANDLE RESULT */
                if(it.isSuccessful) {
                    _newMovieResult?.value = null
                } else {
                    _newMovieResult?.value = it.exception
                }
            }
    }
}