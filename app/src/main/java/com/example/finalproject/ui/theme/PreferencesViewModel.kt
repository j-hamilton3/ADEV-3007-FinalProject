package com.example.finalproject.ui.theme
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PreferencesViewModel : ViewModel() {
    private val _isDarkThemeEnabled = MutableStateFlow(false)
    val isDarkThemeEnabled = _isDarkThemeEnabled.asStateFlow()

    fun toggleTheme(isEnabled: Boolean) {
        viewModelScope.launch {
            _isDarkThemeEnabled.value = isEnabled
        }
    }
}