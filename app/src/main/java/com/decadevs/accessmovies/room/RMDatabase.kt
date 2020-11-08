package com.decadevs.accessmovies.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.decadevs.accessmovies.data.Comment
import com.decadevs.accessmovies.data.CommentR
import com.decadevs.accessmovies.data.Movie
import com.decadevs.accessmovies.data.MovieR

@Database(entities = [MovieR::class, CommentR::class], version = 1, exportSchema = false)
abstract class RMDatabase(): RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun commentDao(): CommentDao

    companion object {
        @Volatile
        private var INSTANCE: RMDatabase? = null

        fun getDatabase(context: Context): RMDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }

            /** CREATE INSTANCE OF DATABASE */
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RMDatabase::class.java,
                    "movies_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}