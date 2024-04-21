import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.network.GameApiService
import com.example.finalproject.ui.theme.GameDetailsViewModel

class GameDetailsViewModelFactory(private val apiService: GameApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameDetailsViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
