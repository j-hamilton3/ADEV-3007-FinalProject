package com.example.finalproject.ui.theme

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.network.GameAPI
import kotlinx.coroutines.launch

class GameViewModel: ViewModel() {

    init {
        getAllGames()
    }

    private fun getAllGames() {
        viewModelScope.launch {
            val listResult = GameAPI.retrofitService.getGames()
            Log.w("API_RESULTS", listResult.toString())
        }
    }
}