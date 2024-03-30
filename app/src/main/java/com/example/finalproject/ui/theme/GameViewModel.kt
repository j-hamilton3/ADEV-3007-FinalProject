package com.example.finalproject.ui.theme

import android.app.GameState
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.Game
import com.example.finalproject.network.GameAPI
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface GameUiState {
    data class Success(val games : List<Game>) : GameUiState
    object Error : GameUiState
    object Loading: GameUiState
}

class GameViewModel: ViewModel() {
    var gameUiState : GameUiState by mutableStateOf(GameUiState.Loading)
        private set

    init {
        getAllGames()
    }

    private fun getAllGames() {
        viewModelScope.launch {
            gameUiState = try {
                GameUiState.Success(GameAPI.retrofitService.getGames())
            } catch (e: IOException){
                GameUiState.Error
            }
        }
    }
}