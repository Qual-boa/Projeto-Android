import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qualaboaapp.ui.theme.home.top_estabelecimentos.Establishment
import com.example.qualaboaapp.ui.theme.home.top_estabelecimentos.EstablishmentPhoto
import com.example.qualaboaapp.ui.theme.home.top_estabelecimentos.EstablishmentsRepository
import com.example.qualaboaapp.ui.theme.search.BarRepository
import com.example.qualaboaapp.ui.theme.search.BarResponse
import com.example.qualaboaapp.ui.theme.search.Category
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BarViewModel(
    private val repository: BarRepository,
    private val photoRepository: EstablishmentsRepository
) : ViewModel() {

    val bars = MutableStateFlow<List<BarResponse>>(emptyList())
    val isLoading = MutableStateFlow(false)
    private val _userLocation = MutableStateFlow<Location?>(null)
    val _barDistances = MutableStateFlow<Map<String, Float>>(emptyMap())
    private val _establishmentPhotos =
        MutableStateFlow<Map<String, List<EstablishmentPhoto>>>(emptyMap())
    val establishmentPhotos: StateFlow<Map<String, List<EstablishmentPhoto>>> get() = _establishmentPhotos

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

    fun setUserLocation(location: Location) {
        location.latitude = -23.558056667527953
        location.longitude = -46.66162817662198
        _userLocation.value = location
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    /**
     * Inicializa o cliente de localização.
     */
    fun initializeLocationClient(context: Context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    }

    fun fetchBarsWithDistances(searchTerm: String) {
        viewModelScope.launch {
            isLoading.value = true
            try {

                val results = repository.searchBars(searchTerm, buildCategories())
                if (results.isNotEmpty()) {
                    bars.value = results

                    Log.d("SearchResponseBody", results.toString())

                    loadPhotosForEstablishments(results)
                    val userLoc = _userLocation.value
                    if (userLoc != null) {
                        val distances = results.associate { bar ->
                            val barLocation = repository.getBarCoordinates(bar.id)
                            val distance = if (barLocation != null) {
                                val resultsArray = FloatArray(1)
                                Location.distanceBetween(
                                    userLoc.latitude,
                                    userLoc.longitude,
                                    barLocation.latitude,
                                    barLocation.longitude,
                                    resultsArray
                                )
                                resultsArray[0] / 1000 // Convert to km
                            } else {
                                -1f // Indicar distância desconhecida
                            }
                            bar.id to distance
                        }
                        _barDistances.value = distances
                    } else {
                        Log.e("BarViewModel", "Localização do usuário não disponível.")
                    }
                } else {
                    Log.d("BarViewModel", "Nenhum bar encontrado para os critérios fornecidos.")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                bars.value = emptyList()
            } finally {
                isLoading.value = false
            }
        }
    }

    private suspend fun loadPhotosForEstablishments(establishments: List<BarResponse>) {
        val photosMap = mutableMapOf<String, List<EstablishmentPhoto>>()
        establishments.forEach { establishment ->
            try {
                val photos = photoRepository.fetchEstablishmentPhotos(establishment.id)
                photosMap[establishment.id] = photos
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        _establishmentPhotos.emit(photosMap)
        Log.d("EstablishmentPhotosFound", photosMap.toString())
    }

    // Helper function to get the Google API Key
    fun getGoogleApiKey(context: Context): String {
        val appInfo = context.packageManager.getApplicationInfo(
            context.packageName,
            PackageManager.GET_META_DATA
        )
        return appInfo.metaData.getString("com.google.android.geo.API_KEY")
            ?: throw IllegalStateException("Google API Key not found in AndroidManifest.xml")
    }

    fun buildCategories(): List<Category> {
        val musics = listOf("Rock", "Sertanejo", "Indie", "Rap", "Funk", "Metal")
        val foods =
            listOf("Brasileira", "Boteco", "Japonesa", "Mexicana", "Churrasco", "Hamburguer")
        val drinks =
            listOf("Cerveja", "Vinho", "Chopp", "Whisky", "Gim", "Caipirinha", "Drinks")

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

    fun clearBars() {
        bars.value = emptyList()
    }
}

