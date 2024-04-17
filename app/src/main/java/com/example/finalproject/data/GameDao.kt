package com.example.finalproject.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.finalproject.model.Game

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(game: Game)

    @Query("SELECT * FROM games ORDER BY title DESC")
    suspend fun getAllItems(): List<Game>

    @Update
    suspend fun update(game: Game)

    @Delete
    suspend fun delete(game: Game)

    @Query("DELETE FROM games WHERE title = :title AND userId = :userId")
    suspend fun deleteTitle(title: String, userId: String?)
}