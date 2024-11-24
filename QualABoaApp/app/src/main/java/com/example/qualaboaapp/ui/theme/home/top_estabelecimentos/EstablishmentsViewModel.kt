package com.example.qualaboaapp.ui.theme.home.top_estabelecimentos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EstablishmentsViewModel(
    private val repository: EstablishmentsRepository
) : ViewModel() {

    private val _topEstablishments = MutableStateFlow<List<Establishment>>(emptyList())
    val topEstablishments: StateFlow<List<Establishment>> = _topEstablishments

    private val _establishmentPhotos = MutableStateFlow<Map<String, List<String>>>(emptyMap())
    val establishmentPhotos: StateFlow<Map<String, List<String>>> = _establishmentPhotos

    init {
        fetchTopEstablishments()
    }

    private fun fetchTopEstablishments() {
        viewModelScope.launch {
            try {
                val establishments = repository.fetchTopEstablishments()
                val photos = establishments.associate { establishment ->
                    establishment.id to repository.fetchEstablishmentPhotos(establishment.id)
                }
                _topEstablishments.emit(establishments.sortedByDescending { it.rating }.take(5))
                _establishmentPhotos.emit(photos)
            } catch (e: Exception) {
                println("Error fetching establishments: ${e.message}")
            }
        }
    }
}
