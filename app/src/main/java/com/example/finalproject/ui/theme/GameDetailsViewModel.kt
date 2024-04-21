package com.example.finalproject.ui.theme
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.model.GameDetails
import com.example.finalproject.network.GameApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameDetailsViewModel(private val apiService: GameApiService) : ViewModel() {

    private val _gameDetails = MutableStateFlow<GameDetails?>(null)
    val gameDetails = _gameDetails.asStateFlow()

    fun fetchGameDetails(gameId: Int) {
        viewModelScope.launch {
            try {
                val details = apiService.getGameDetails(gameId)
                _gameDetails.value = details
            } catch (e: Exception) {
                Log.d("API Error", "Error fetching game details: ${e.message}")
            }
        }
    }
}