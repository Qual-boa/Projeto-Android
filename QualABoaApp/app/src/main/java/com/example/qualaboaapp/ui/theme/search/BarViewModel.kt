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
import com.example.qualaboaapp.ui.theme.search.FavoriteRequestBody
import com.example.qualaboaapp.ui.theme.utils.UserPreferences
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BarViewModel(
    private val repository: BarRepository,
    private val userPreferences: UserPreferences,
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

    private val _favorites = MutableStateFlow<List<String>>(emptyList())
    val favorites: StateFlow<List<String>> get() = _favorites

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

    fun fetchBarsWithDistances(searchTerm: String, userId: String?) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val results = repository.searchBars(searchTerm, buildCategories())
                if (results.isNotEmpty()) {
                    val favoriteIds = favorites.value
                    bars.value = results.map { bar ->
                        bar.copy(isFavorite = favoriteIds.contains(bar.id))
                    }

                    loadPhotosForEstablishments(results)
                    calculateDistances(results)
                } else {
                    bars.value = emptyList()
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

    private suspend fun calculateDistances(bars: List<BarResponse>) {
        val userLoc = _userLocation.value
        if (userLoc != null) {
            val distances = bars.associate { bar ->
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


    fun fetchUserFavorites(userId: String?, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                // Resolver o ID do usuário (validação de login)
                val resolvedUserId = userId?.takeIf { it.isNotBlank() }
                    ?: userPreferences.getUserId()?.takeIf { it.isNotBlank() }
                    ?: run {
                        onError("Usuário não está logado. Por favor, faça login para continuar.")
                        return@launch
                    }

                // Buscar favoritos do usuário
                val favoriteEstablishments = repository.getUserFavorites(resolvedUserId)
                _favorites.value = favoriteEstablishments.map { it.id }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("BarViewModel", "Erro ao buscar favoritos: ${e.localizedMessage}")
                onError("Erro ao buscar favoritos: ${e.localizedMessage}")
            }
        }
    }

    fun fetchFavoriteBars(userId: String?, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                // Resolver o ID do usuário
                val resolvedUserId = userId?.takeIf { it.isNotBlank() }
                    ?: userPreferences.getUserId()?.takeIf { it.isNotBlank() }
                    ?: run {
                        onError("Usuário não está logado. Por favor, faça login para continuar.")
                        return@launch
                    }

                isLoading.value = true

                // Buscar os bares favoritos
                val favoriteEstablishments = repository.getUserFavoritesList(resolvedUserId)
                bars.value = favoriteEstablishments

                // Buscar as fotos associadas aos favoritos
                val photosMap = mutableMapOf<String, List<EstablishmentPhoto>>()
                favoriteEstablishments.forEach { favorite ->
                    val photos = try {
                        photoRepository.fetchEstablishmentPhotos(favorite.id)
                    } catch (e: Exception) {
                        emptyList()
                    }
                    photosMap[favorite.id] = photos
                }

                _establishmentPhotos.emit(photosMap) // Atualiza o StateFlow com as fotos
            } catch (e: Exception) {
                e.printStackTrace()
                onError("Erro ao buscar favoritos: ${e.localizedMessage}")
            } finally {
                isLoading.value = false
            }
        }
    }

    fun toggleFavorite(
        establishmentId: String,
        userId: String?,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val resolvedUserId = userId?.takeIf { it.isNotBlank() }
                    ?: userPreferences.getUserId()?.takeIf { it.isNotBlank() }
                    ?: run {
                        onError("Usuário não está logado. Por favor, faça login para continuar.")
                        return@launch
                    }

                val isCurrentlyFavorite = favorites.value.contains(establishmentId)

                if (isCurrentlyFavorite) {
                    // Lógica para desfavoritar
                    val response = repository.unfavoriteEstablishment(resolvedUserId, establishmentId)
                    if (response.isSuccessful) {
                        _favorites.value = _favorites.value - establishmentId
                        onSuccess("Desfavoritado com sucesso!")
                    } else {
                        onError("Erro ao desfavoritar. Tente novamente.")
                    }
                } else {
                    // Lógica para favoritar
                    val response = repository.favoriteEstablishment(
                        FavoriteRequestBody(
                            establishmentId = establishmentId,
                            userId = resolvedUserId
                        )
                    )
                    if (response.isSuccessful) {
                        _favorites.value = _favorites.value + establishmentId
                        onSuccess("Favoritado com sucesso!")
                    } else {
                        onError("Erro ao favoritar. Tente novamente.")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onError("Erro ao realizar a operação: ${e.localizedMessage}")
            }
        }
    }

    fun clearBars() {
        bars.value = emptyList()
    }
}

