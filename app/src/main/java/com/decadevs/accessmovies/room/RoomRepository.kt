package com.decadevs.accessmovies.room

import androidx.lifecycle.MutableLiveData
import com.decadevs.accessmovies.data.Comment
import com.decadevs.accessmovies.data.Movie

class RoomRepository(private val movieDao: MovieDao, private val commentDao: CommentDao) {

    /** GET ALL MOVIES IN DATABASE */
    val getAllMovies: MutableLiveData<List<Movie>> = movieDao.getAllMovies()

    /** ADD NEW MOVIE TO DATABASE */
    suspend fun addMovie(movie: Movie) {
        movieDao.addMovie(movie)
    }

    /** GET ALL COMMENTS IN DATABASE */
    val getAllComments: MutableLiveData<List<Comment>> = commentDao.getAllComments()

    /** ADD NEW COMMENT TO DATABASE */
    suspend fun addComment(comment: Comment) {
        commentDao.addComment(comment)
    }
}