package com.example.finalproject.ui.theme

import android.text.Spannable.Factory
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalproject.GameApplication
import com.example.finalproject.data.AuthRepository


data class SignInUiState(
    val email: String = "",
    val password: String = ""
)
class SignInViewModel(private val authRepository: AuthRepository) : ViewModel() {
    var uiState = mutableStateOf(SignInUiState())
        private set

    fun updatePasswordState(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun updateEmailState(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun registerUser() {
        authRepository.createAccount(uiState.value.email, uiState.value.password) {
            task ->
            Log.d("REGISTRATION", task?.message.toString())
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as GameApplication)
                val authRepository = application.container.authRepository
                SignInViewModel(authRepository = authRepository)
            }
        }
    }
}