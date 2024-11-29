package com.example.qualaboaapp.ui.theme.establishment

import android.content.Context
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qualaboaapp.ui.theme.search.BarRepository
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
    fun getGoogleApiKey(context: Context): String {
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
