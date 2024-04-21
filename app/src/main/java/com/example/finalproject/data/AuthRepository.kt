package com.example.finalproject.data

import com.example.finalproject.network.FirebaseAuthService
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit)

    fun getCurrentUser()

    fun logoutUser()

    var CurrentUser: GameUser?
}

class FirebaseAuthRepository(private val _authService: FirebaseAuthService): AuthRepository {
    override fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        _authService.createAccount(email, password, onResult)
    }

    override fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit) {
        _authService.authenticate(email, password, onResult)
    }

    override fun getCurrentUser() {
        var firebaseUser: FirebaseUser? = _authService.getCurrentUser()

        if(firebaseUser != null){
            CurrentUser = GameUser(
                email = firebaseUser.email ?: "No Email",
                id = firebaseUser.uid ?: "0",
                isSignedIn = true
            )
        } else {
            throw Exception("User not currently logged in.")
        }
    }

    override var CurrentUser: GameUser? = null

    override fun logoutUser() {
        _authService.logout()
    }
}