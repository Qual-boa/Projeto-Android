package com.example.qualaboaapp.ui.theme.establishment

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qualaboaapp.ui.theme.search.BarRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EstablishmentViewModel(private val repository: BarRepository) : ViewModel() {

    // Estado para os dados do estabelecimento
    private val _establishment = MutableStateFlow<EstablishmentResponse?>(null)
    val establishment: StateFlow<EstablishmentResponse?> = _establishment

    // Estado para o endereço do estabelecimento
    private val _address = MutableStateFlow<AddressResponse?>(null)
    val address: StateFlow<AddressResponse?> = _address

    // Estado para as coordenadas (latitude e longitude)
    private val _coordinates = MutableStateFlow<LatLng?>(null)
    val coordinates: StateFlow<LatLng?> = _coordinates

    // Estado de carregamento
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    /**
     * Carrega os dados de um estabelecimento pelo ID.
     */
    fun loadEstablishmentById(id: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getEstablishmentById(id)
                _establishment.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                _establishment.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Recupera a chave da API do Google a partir do Manifest.
     */
    private fun getGoogleApiKey(context: Context): String {
        return try {
            val appInfo = context.packageManager.getApplicationInfo(
                context.packageName,
                PackageManager.GET_META_DATA
            )
            appInfo.metaData.getString("com.google.android.geo.API_KEY")
                ?: throw IllegalStateException("Google API Key not found")
        } catch (e: Exception) {
            e.printStackTrace()
            throw IllegalStateException("Error retrieving Google API Key")
        }
    }

    /**
     * Carrega o endereço e converte para coordenadas geográficas.
     */
    fun loadAddressAndCoordinates(establishmentId: String, context: Context) {
        val apiKey = getGoogleApiKey(context)
        _isLoading.value = true

        viewModelScope.launch {
            try {
                // Buscar endereço pelo ID do estabelecimento
                val addressResponse = repository.getAddressByEstablishmentId(establishmentId)
                _address.value = addressResponse

                // Construir o endereço completo
                val fullAddress = addressResponse?.let { buildFullAddress(it) }

                // Obter coordenadas do endereço usando a API do Google
                val location = fullAddress?.let { repository.getCoordinatesFromAddress(it, apiKey) }
                if (location != null) {
                    android.util.Log.d("COORDINATES", "Latitude: ${location.lat}, Longitude: ${location.lng}")
                }
                // Corrigir aqui: use lat e lng em vez de latitude e longitude
                _coordinates.value = location?.let { LatLng(it.lat, it.lng) }

            } catch (e: Exception) {
                e.printStackTrace()
                _address.value = null
                _coordinates.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Estado para os dados de avaliações enriquecidas com nomes de usuários
    private val _reviewsWithUsers = MutableStateFlow<List<ReviewWithUser>>(emptyList())
    val reviewsWithUsers: StateFlow<List<ReviewWithUser>> = _reviewsWithUsers

    // Função para carregar os dados de avaliações com nomes de usuários
    fun loadReviewsWithUsers(relationships: List<Relationship>) {
        viewModelScope.launch {
            val enrichedReviews = relationships
                .filter { it.interactionType == "COMMENT" }
                .map { relationship ->
                    try {
                        val user = repository.getUserById(relationship.userId)
                        ReviewWithUser(
                            userName = user.name,
                            rate = relationship.rate,
                            message = relationship.message
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                        ReviewWithUser(
                            userName = "Usuário Desconhecido",
                            rate = relationship.rate,
                            message = relationship.message
                        )
                    }
                }
            _reviewsWithUsers.value = enrichedReviews
        }
    }

    private val _userLocation = MutableStateFlow<Location?>(null)
    val userLocation: StateFlow<Location?> = _userLocation

    private val _barDistances = MutableStateFlow<Map<String, Float>>(emptyMap())
    val barDistances: StateFlow<Map<String, Float>> = _barDistances

    /**
     * Constrói o endereço completo a partir do [AddressResponse].
     */
    private fun buildFullAddress(addressResponse: AddressResponse): String {
        return with(addressResponse) {
            listOfNotNull(
                street,
                number,
                neighborhood,
                city,
                state,
                postalCode
            ).joinToString(", ")
        }
    }
}

// Classe para representar os comentários enriquecidos
data class ReviewWithUser(
    val userName: String,
    val rate: Double,
    val message: String
)
