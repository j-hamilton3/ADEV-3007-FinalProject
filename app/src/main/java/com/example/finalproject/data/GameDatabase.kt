package com.example.finalproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.finalproject.model.Game
import kotlinx.coroutines.InternalCoroutinesApi

@Database(
    entities = [Game::class],
    version = 1,
    exportSchema = false
)
abstract class GameDatabase: RoomDatabase() {
    abstract fun gameDao(): GameDao

    companion object {
        @Volatile
        private var Instance: GameDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context): GameDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, GameDatabase::class.java, "game_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {Instance = it}
            }
        }
    }
}