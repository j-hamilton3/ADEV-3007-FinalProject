package com.example.finalproject.ui.theme

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
    val password: String = "",
    val uiMessage: String? = null
)
class SignInViewModel(private val authRepository: AuthRepository) : ViewModel() {
    var uiState = mutableStateOf(SignInUiState())
        private set

    lateinit var navigateOnSignIn: () -> Unit

    fun updatePasswordState(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun updateEmailState(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun registerUser() {
        authRepository.createAccount(uiState.value.email, uiState.value.password) {
            throwable ->
            if(throwable != null) {
                uiState.value = uiState.value.copy(
                    uiMessage = throwable.message ?: "There was an error registering your account."
                )
            } else {
                uiState.value = uiState.value.copy(
                    uiMessage = "Successfully registered ${uiState.value.email}"
                )
                //navigateOnSignIn() // Get rid of this?
            }
        }
    }

    fun authenticateUser() {
        authRepository.authenticate(uiState.value.email, uiState.value.password) {
                throwable ->
            if(throwable != null) {
                uiState.value = uiState.value.copy(
                    uiMessage = throwable.message ?: "There was an logging into your account."
                )
            } else {
                uiState.value = uiState.value.copy(
                    uiMessage = "Successfully logged into ${uiState.value.email}"
                )
                try {
                    authRepository.getCurrentUser()
                    Log.d("LOGIN", authRepository.CurrentUser.toString())
                    navigateOnSignIn()
                } catch(e :Exception) {
                    uiState.value = uiState.value.copy(
                        uiMessage = e.message ?: "There was an error registering your account."
                    )
                }
            }
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