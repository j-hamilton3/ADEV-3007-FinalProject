package com.example.finalproject.ui.theme

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class SignInUiState(
    val email: String = "",
    val password: String = ""
)
class SignInViewModel : ViewModel() {
    var uiState = mutableStateOf(SignInUiState())
        private set

    fun updatePasswordState(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun updateEmailState(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }
}