package com.example.finalproject.data

import com.example.finalproject.network.FirebaseAuthService

interface AuthRepository {
    fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit)
}

class FirebaseAuthRepository(private val _authService: FirebaseAuthService): AuthRepository {
    override fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        _authService.createAccount(email, password, onResult)
    }
}