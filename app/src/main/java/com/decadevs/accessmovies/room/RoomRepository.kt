package com.decadevs.accessmovies.room

import androidx.lifecycle.LiveData
import com.decadevs.accessmovies.data.Comment
import com.decadevs.accessmovies.data.CommentR
import com.decadevs.accessmovies.data.Movie
import com.decadevs.accessmovies.data.MovieR

class RoomRepository(private val movieDao: MovieDao, private val commentDao: CommentDao) {

    /** GET ALL MOVIES IN DATABASE */
    val getAllMovies: LiveData<List<MovieR>> = movieDao.getAllMovies()

    /** ADD NEW MOVIE TO DATABASE */
    suspend fun addMovie(movie: MovieR) {
        movieDao.addMovie(movie)
    }

    /** GET ALL COMMENTS IN DATABASE */
    val getAllComments: LiveData<List<CommentR>> = commentDao.getAllComments()

    /** ADD NEW COMMENT TO DATABASE */
    suspend fun addComment(comment: CommentR) {
        commentDao.addComment(comment)
    }
}