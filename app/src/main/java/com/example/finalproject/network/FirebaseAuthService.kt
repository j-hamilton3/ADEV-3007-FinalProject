package com.example.finalproject.network

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class FirebaseAuthService {
    fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                task ->
                if(task.isSuccessful) {
                    onResult(null)
                } else {
                    onResult(Exception(task.exception?.message))
                }
            }
    }

    fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                task ->
            if(task.isSuccessful) {
                onResult(null)
            } else {
                onResult(Exception(task.exception?.message))
            }
        }
    }

    fun logout() {
        Firebase.auth.signOut()
    }

    fun getCurrentUser(): FirebaseUser? {
        return Firebase.auth.currentUser
    }
}