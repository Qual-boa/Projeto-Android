import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qualaboaapp.ui.theme.home.top_estabelecimentos.Establishment
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FavoritesViewModel(
    private val favoriteApi: FavoriteApi,
    private val isUserLoggedIn: () -> Boolean // Validação do login via injeção
) : ViewModel() {

    // Fluxo para armazenar os favoritos
    private val _favorites = MutableStateFlow<List<Establishment>>(emptyList())
    val favorites: StateFlow<List<Establishment>> = _favorites

    // Fluxo para gerenciar estado de carregamento
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Fluxo para gerenciar mensagens de erro
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    /**
     * Busca os favoritos do usuário, mas apenas se ele estiver logado.
     */
    fun fetchFavorites(userId: String) {
        viewModelScope.launch {
            if (!isUserLoggedIn()) {
                _errorMessage.value = "Usuário não está logado."
                return@launch
            }

            _isLoading.value = true // Indica que o carregamento começou
            try {
                val response = favoriteApi.getFavorites(userId)
                _favorites.value = response // Atualiza os favoritos
                _errorMessage.value = null // Limpa possíveis mensagens de erro
            } catch (e: Exception) {
                _errorMessage.value = "Erro ao buscar favoritos: ${e.message}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false // Finaliza o carregamento
            }
        }
    }

    /**
     * Limpa mensagens de erro.
     */
    fun clearError() {
        _errorMessage.value = null
    }
}
