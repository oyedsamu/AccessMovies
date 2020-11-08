package com.decadevs.accessmovies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.decadevs.accessmovies.data.Comment
import com.decadevs.accessmovies.data.Movie
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class MovieViewModel(): ViewModel() {
    val moviesDatabase = FirebaseDatabase.getInstance().reference.child("movies")
    val commentsDatabase = FirebaseDatabase.getInstance().reference

    // Write a message to the database
    var database = FirebaseDatabase.getInstance().getReference("access-movies");



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
        get() = _newMovieResult

    /** LIVEDATA TO HOLD LIST OF MOVIES */
    private val _comments: MutableLiveData<List<Comment>>? = null
    val comments: LiveData<List<Comment>>?
        get() = _comments



    fun addNewMovie(movie: Movie) {
        database.child("1").setValue("Hello, World!");
//        /** ADD NEW MOVIE */
//        movie.id = moviesDatabase.push().key.toString()
//        moviesDatabase.child(movie.id).setValue(movie)
//            .addOnCompleteListener{
//                /** HANDLE RESULT */
//                if(it.isSuccessful) {
//                    _newMovieResult?.value = null
//                } else {
//                    _newMovieResult?.value = it.exception
//                }
//            }
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
                        comment?.id = commentShot.key.toString()

                        /** CHECK FOR NULLABILITY */
                        comment?.let{comments.add(it)}
                    }
                    _comments?.value = comments
                }
            }
        })
    }
}