package com.example.finalproject.data

import com.example.finalproject.model.Game

interface GameStorageRepository {
    suspend fun getAllGames(): List<Game>
    suspend fun insertGame(game: Game)
    suspend fun updateGame(game: Game)
    suspend fun deleteGame(game:Game)

    suspend fun deleteTitle(title: String, userId : String?)
}

class LocalGameStorageRepository(private val gameDao: GameDao): GameStorageRepository {
    override suspend fun getAllGames(): List<Game> {
        return gameDao.getAllItems()
    }

    override suspend fun insertGame(game: Game) {
        gameDao.insert(game)
    }

    override suspend fun updateGame(game: Game) {
        gameDao.update(game)
    }

    override suspend fun deleteGame(game: Game) {
        gameDao.delete(game)
    }

    override suspend fun deleteTitle(title: String, userId: String?) {
        gameDao.deleteTitle(title, userId)
    }

}