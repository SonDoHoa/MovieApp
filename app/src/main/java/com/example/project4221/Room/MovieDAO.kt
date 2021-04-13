package com.example.project4221.Room

import androidx.room.*

@Dao
interface MovieDAO {
    @Query("SELECT * FROM Movies_Database")
    fun getAll(): List<MoviesEntity>

    @Query("SELECT * FROM Movies_Database WHERE name LIKE :name")
    fun findByName(name: String): MoviesEntity

    @Query("SELECT * FROM Movies_Database WHERE id =:id")
    fun findById(id: String): MoviesEntity

    @Insert
    fun insertAll(vararg moviesEntity: MoviesEntity): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: MoviesEntity): Long

    @Delete
    fun delete(moviesEntity: MoviesEntity)

    @Update
    fun Update(moviesEntity: MoviesEntity)

    @Query("DELETE FROM Movies_Database")
    fun deleteAll()
}