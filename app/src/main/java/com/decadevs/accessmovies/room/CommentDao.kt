package com.decadevs.accessmovies.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.decadevs.accessmovies.data.CommentR

@Dao
interface CommentDao {
    /** GET ALL COMMENTS FROM DATABASE */
    @Query("SELECT * FROM comments_table ORDER BY id ASC")
    fun getAllComments(): LiveData<List<CommentR>>

    /** ADD MOVIE TO DATABASE */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addComment(comment: CommentR)
}