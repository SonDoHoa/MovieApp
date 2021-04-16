package com.example.project4221.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MoviesEntity::class], version = 1)
abstract class MoviesDatabase : RoomDatabase(){
    abstract fun movieDAO(): MovieDAO

    companion object{
        @Volatile
        private var instance: MoviesDatabase?=null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK){
            instance
                ?: buildDatabase(
                    context
                )
                    .also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context, MoviesDatabase::class.java, "Favorite.db"
        ).allowMainThreadQueries()
            .build()
    }
}