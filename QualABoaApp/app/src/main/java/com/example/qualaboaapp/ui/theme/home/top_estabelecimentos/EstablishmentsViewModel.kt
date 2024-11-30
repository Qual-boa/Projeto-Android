package com.example.qualaboaapp.ui.theme.home.top_estabelecimentos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qualaboaapp.ui.theme.home.categorias.Category
import com.example.qualaboaapp.ui.theme.utils.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class EstablishmentsViewModel(
    private val repository: EstablishmentsRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _topEstablishments = MutableStateFlow<List<Establishment>>(emptyList())
    val topEstablishments: StateFlow<List<Establishment>> get() = _topEstablishments

    private val _establishmentPhotos =
        MutableStateFlow<Map<String, List<EstablishmentPhoto>>>(emptyMap())
    val establishmentPhotos: StateFlow<Map<String, List<EstablishmentPhoto>>> get() = _establishmentPhotos

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    private val _bestCategoryEstablishments = MutableStateFlow<List<Establishment>>(emptyList())
    val bestCategoryEstablishments: StateFlow<List<Establishment>> get() = _bestCategoryEstablishments

    init {
        loadEstablishments()
    }

    private fun loadEstablishments() {
        viewModelScope.launch {
            _loading.emit(true)
            _errorMessage.emit(null)

            try {
                val savedEstablishments = userPreferences.establishments.firstOrNull()
                if (!savedEstablishments.isNullOrEmpty()) {
                    _topEstablishments.emit(savedEstablishments.sortedByDescending { it.averageOrderValue }
                        .take(5))
                    loadPhotosForEstablishments(savedEstablishments)
                } else {
                    val fetchedEstablishments = repository.fetchTopEstablishments()
                    _topEstablishments.emit(fetchedEstablishments.sortedByDescending { it.averageOrderValue }
                        .take(5))
                    userPreferences.saveEstablishments(fetchedEstablishments)
                    loadPhotosForEstablishments(fetchedEstablishments)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.emit("Erro ao carregar estabelecimentos: ${e.message}")
            } finally {
                _loading.emit(false)
            }
        }
    }

    private suspend fun loadPhotosForEstablishments(establishments: List<Establishment>) {
        val photosMap = mutableMapOf<String, List<EstablishmentPhoto>>()
        establishments.forEach { establishment ->
            try {
                val photos = repository.fetchEstablishmentPhotos(establishment.id)
                photosMap[establishment.id] = photos
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        _establishmentPhotos.emit(photosMap)
    }

    fun loadBestCategoryEstablishments(popularCategories: List<Category>) {
        viewModelScope.launch {
            val allEstablishments = userPreferences.establishments.firstOrNull() ?: emptyList()
            println("All Establishments: $allEstablishments")
            println("Popular Categories: $popularCategories")

            val filteredEstablishments = allEstablishments.filter { establishment ->
                establishment.categories?.any { category ->
                    popularCategories.any { it.name.equals(category.name, ignoreCase = true) }
                } == true
            }

            println("Filtered Establishments: $filteredEstablishments")
            _bestCategoryEstablishments.emit(filteredEstablishments)
        }
    }



}
