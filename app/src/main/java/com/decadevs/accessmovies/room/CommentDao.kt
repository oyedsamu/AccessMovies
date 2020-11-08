package com.decadevs.accessmovies.room

import androidx.lifecycle.MutableLiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decadevs.accessmovies.data.Comment
import com.decadevs.accessmovies.data.Movie

interface CommentDao {
    /** GET ALL COMMENTS FROM DATABASE */
    @Query("SELECT * FROM comments_table ORDER BY id ASC")
    fun getAllComments(): MutableLiveData<List<Comment>>

    /** ADD MOVIE TO DATABASE */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addComment(comment: Comment)
}