package com.example.finalproject

import android.content.Context
import com.example.finalproject.data.AuthRepository
import com.example.finalproject.data.FirebaseAuthRepository
import com.example.finalproject.data.GameDatabase
import com.example.finalproject.data.GameStorageRepository
import com.example.finalproject.data.LocalGameStorageRepository
import com.example.finalproject.network.FirebaseAuthService

interface AppContainer {
    val authRepository: AuthRepository
    val gameStorageRepository: GameStorageRepository
}
class DefaultAppContainer(private val context: Context) : AppContainer {
    override val authRepository: AuthRepository by lazy {
        FirebaseAuthRepository(FirebaseAuthService())
    }
    override val gameStorageRepository by lazy{
        LocalGameStorageRepository(GameDatabase.getDatabase(context).gameDao())
    }

}