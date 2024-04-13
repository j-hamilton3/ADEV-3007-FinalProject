package com.example.finalproject

import android.content.Context
import com.example.finalproject.data.AuthRepository
import com.example.finalproject.data.FirebaseAuthRepository
import com.example.finalproject.network.FirebaseAuthService

interface AppContainer {
    val authRepository: AuthRepository
}
class DefaultAppContainer(private val context: Context) : AppContainer {
    override val authRepository: AuthRepository by lazy {
        FirebaseAuthRepository(FirebaseAuthService())
    }
}