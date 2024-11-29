import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qualaboaapp.ui.theme.search.BarRepository
import com.example.qualaboaapp.ui.theme.search.BarResponse
import com.example.qualaboaapp.ui.theme.search.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class BarViewModel(private val repository : BarRepository) : ViewModel() {

    val bars = MutableStateFlow<List<BarResponse>>(emptyList())
    val isLoading = MutableStateFlow(false)

    // Estados para categorias selecionadas
    private val selectedMusics = MutableStateFlow<List<String>>(emptyList())
    private val selectedFoods = MutableStateFlow<List<String>>(emptyList())
    private val selectedDrinks = MutableStateFlow<List<String>>(emptyList())

    // Atualizar seleção de categorias
    fun updateSelectedMusics(musics: List<String>) {
        selectedMusics.value = musics
    }

    fun updateSelectedFoods(foods: List<String>) {
        selectedFoods.value = foods
    }

    fun updateSelectedDrinks(drinks: List<String>) {
        selectedDrinks.value = drinks
    }

    private fun buildCategories(): List<Category> {
        val musics = listOf("Rock", "Sertanejo", "Indie", "Rap", "Funk", "Metal")
        val foods = listOf("Brasileira", "Boteco", "Japonesa", "Mexicana", "Churrasco", "Hamburguer")
        val drinks = listOf("Cerveja", "Vinho", "Chopp", "Whisky", "Gim", "Caipirinha", "Drinks")

        return selectedDrinks.value.map { item ->
            Category(
                categoryType = 3,
                category = drinks.indexOf(item) + 1
            )
        } + selectedFoods.value.map { item ->
            Category(
                categoryType = 2,
                category = foods.indexOf(item) + 1
            )
        } + selectedMusics.value.map { item ->
            Category(
                categoryType = 1,
                category = musics.indexOf(item) + 1
            )
        }
    }

    fun searchBars(searchTerm: String) {
        isLoading.value = true
        val categories = buildCategories()

        viewModelScope.launch {
            try {
                val response = repository.searchBars(searchTerm, categories)
                bars.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                bars.value = emptyList()
            } finally {
                isLoading.value = false
            }
        }
    }

    fun clearBars() {
        bars.value = emptyList()
    }
}
