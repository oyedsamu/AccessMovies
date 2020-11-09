package com.decadevs.accessmovies.viewmodel

import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.room.Database
import com.decadevs.accessmovies.R
import com.decadevs.accessmovies.data.Comment
import com.decadevs.accessmovies.data.Movie
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import java.lang.Exception
import java.lang.ref.Reference

class MovieViewModel(): ViewModel() {

    /** CREATE PATH FOR MOVIES AND COMMENTS IN THE DATABASE */
    var moviesDatabase = FirebaseDatabase.getInstance().getReference("Movies");
    var commentsDatabase = FirebaseDatabase.getInstance().getReference("Comments");

    /** LIVEDATA TO HOLD NEW MOVIE RESULT */
    private val _newMovieResult: MutableLiveData<Exception>? = null
    val newMovieResult: LiveData<Exception>?
        get() = _newMovieResult

    /** LIVEDATA TO HOLD LIST OF MOVIES */
    private val _movies: MutableLiveData<List<Movie>>? = null
    val movies: LiveData<List<Movie>>?
        get() = _movies

    /** LIVEDATA TO HOLD NEW COMMENT RESULT */
    private val _newCommentResult: MutableLiveData<Exception>? = null
    val newCommentResult: LiveData<Exception>?
        get() = _newCommentResult

    /** LIVEDATA TO HOLD LIST OF MOVIES */
    private val _comments: MutableLiveData<List<Comment>>? = null
    val comments: LiveData<List<Comment>>?
        get() = _comments



    fun addNewMovie(movie: Movie) {
//        database.child("1").setValue("Hello, World!");
        /** ADD NEW MOVIE */
        movie.id = moviesDatabase.push().key.toString()
        moviesDatabase.child(movie.id).setValue(movie)
            .addOnCompleteListener(){
                Log.d("movie", "hgfcdxrcfvgbhjnkm")

                /** HANDLE RESULT */
                if(it.isSuccessful) {
                    _newMovieResult?.value = null
                } else {
                    _newMovieResult?.value = it.exception
                }
            }
    }

    fun getAllMovies() {
        moviesDatabase.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    val movies = mutableListOf<Movie>()
                    for(movieShot in snapshot.children) {
                        val movie = movieShot.getValue(Movie::class.java)
                        movie?.id = movieShot.key.toString()

                        /** CHECK FOR NULLABILITY */
                        movie?.let{movies.add(it)}
                    }
                    _movies?.value = movies
                }
            }
        })
    }

    fun addNewComment(comment: Comment) {
        /** ADD NEW COMMENT */
        comment.id = commentsDatabase.push().key.toString()
        commentsDatabase.child(comment.id).setValue(comment)
            .addOnCompleteListener{

                /** HANDLE RESULT */
                if(it.isSuccessful) {
                    _newCommentResult?.value = null
//                    Log.d("New Comment", "Successfully added")
                } else {
                    _newCommentResult?.value = it.exception
                }
            }
    }

    fun getAllComments() {
        commentsDatabase.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    val comments = mutableListOf<Comment>()
                    for(commentShot in snapshot.children) {
                        val comment = commentShot.getValue(Comment::class.java)
//                        comment?.movieId = commentShot.key.toString()
                        comment?.movieId = commentShot.key.toString()

                        /** CHECK FOR NULLABILITY */
                        comment?.let{comments.add(it)}
                    }
                    _comments?.value = comments
                }
            }
        })
    }

}