package com.example.qualaboaapp.ui.theme.search

import android.content.Context
import android.util.Log
import com.example.qualaboaapp.R
import com.example.qualaboaapp.ui.theme.establishment.AddressResponse
import com.example.qualaboaapp.ui.theme.establishment.EstablishmentResponse
import com.example.qualaboaapp.ui.theme.establishment.GeocodingApi
import com.example.qualaboaapp.ui.theme.establishment.Location
import com.example.qualaboaapp.ui.theme.establishment.getGoogleApiKey
import com.example.qualaboaapp.ui.theme.home.categorias.RetrofitService
import com.example.qualaboaapp.ui.theme.home.top_estabelecimentos.EstablishmentPhoto
import com.example.qualaboaapp.ui.theme.home.top_estabelecimentos.EstablishmentPhotoApi
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Response
import javax.net.ssl.HostnameVerifier

open class BarRepository(val context: Context) {
    private val api = RetrofitService.provideRetrofit(context).create(ApiService::class.java)
    private val photoApi = RetrofitService.provideRetrofit(context).create(EstablishmentPhotoApi::class.java)

    suspend fun searchBars(searchTerm: String, categories: List<Category>): List<BarResponse> {
        val requestBody = SearchRequest(
            name = searchTerm,
            categories = categories
        )
        Log.d("SearchRequestBody", requestBody.toString())

        return withContext(Dispatchers.IO) {
            api.searchBars(requestBody) ?: emptyList() // Retorna uma lista vazia se a resposta for nula
        }
    }

    open suspend fun getEstablishmentById(id: String): EstablishmentResponse {
        return withContext(Dispatchers.IO) {
            api.getEstablishmentById(id)
        }
    }

    open suspend fun getAddressByEstablishmentId(establishmentId: String): AddressResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getAddressByEstablishmentId(establishmentId)
                Log.d("ADDRESS_RESPONSE", response.toString())

                if (response.isNotEmpty()) {
                    response.first() // Retorna o primeiro endereço na lista
                } else {
                    null // Trata o caso de lista vazia
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun fetchEstablishmentPhotos(establishmentId: String): List<EstablishmentPhoto> {
        Log.d("RequestPhotoFromSearch", establishmentId)
        try {
            val photos = photoApi.getEstablishmentPhotos(establishmentId).map { photo ->
                Log.d("PhotoResponseFromSearch", photo.toString())
                EstablishmentPhoto(
                    id = photo.id,
                    establishmentId = photo.establishmentId,
                    establishmentCategory = photo.establishmentCategory,
                    imgUrl = photo.imgUrl,
                    originalFilename = photo.originalFilename
                )
            }
            Log.d("Photos", photos.toString())
            return photos
        } catch (e: HttpException) {
            if (e.code() == 500) {
                Log.e("API_ERROR", "Erro 500 no servidor: ${e.response()?.errorBody()?.string()}")
            }
            throw e
        } catch (e: Exception) {
            Log.e("GENERAL_ERROR", "Erro geral: ${e.message}")
            throw e
        }
    }

    suspend fun getBarCoordinates(establishmentId: String): LatLng? {
        return try {
            val address = getAddressByEstablishmentId(establishmentId)
            if (address != null) {
                val fullAddress = listOfNotNull(
                    address.street,
                    address.number,
                    address.neighborhood,
                    address.city,
                    address.state,
                    address.postalCode
                ).joinToString(", ")

                val apiKey = getGoogleApiKey(context)
                val location = getCoordinatesFromAddress(fullAddress, apiKey)
                location?.let { LatLng(it.lat, it.lng) }
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun getCoordinatesFromAddress(address: String, apiKey: String): Location? {
        val geocodingUrl = "https://maps.googleapis.com/maps/api/geocode/json"
        val fullUrl = "$geocodingUrl?address=${address.replace(" ", "+")}&key=$apiKey"

        return try {
            val response = RetrofitService.provideRetrofit(context).create(GeocodingApi::class.java).getCoordinates(fullUrl)
            if (response.status == "OK" && response.results.isNotEmpty()) {
                response.results.first().geometry.location
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun updateEstablishmentRelationship(requestBody: FavoriteRequestBody): Response<Unit> {
        return withContext(Dispatchers.IO) {
            api.updateEstablishmentRelationship(requestBody)
        }
    }

    suspend fun getUserFavorites(userId: String): List<BarResponse> {
        return withContext(Dispatchers.IO) {
            api.getUserFavorites(userId)
        }
    }

    suspend fun getUserFavoritesList(userId: String): List<BarResponse> {
        return withContext(Dispatchers.IO) {
            api.getUserFavoritesList(userId)
        }
    }

    suspend fun favoriteEstablishment(requestBody: FavoriteRequestBody): Response<Unit> {
        return withContext(Dispatchers.IO) {
            api.favoriteEstablishment(requestBody)
        }
    }

    suspend fun unfavoriteEstablishment(userId: String, establishmentId: String): Response<Unit> {
        return withContext(Dispatchers.IO) {
            api.unfavoriteEstablishment(UnfavoriteRequestBody(userId, establishmentId))
        }
    }

    // Função para criar OkHttpClient seguro com HttpLoggingInterceptor
    private fun createSecureOkHttpClient(context: Context): OkHttpClient {
        val certificateFactory = java.security.cert.CertificateFactory.getInstance("X.509")

        // Carregar certificados
        val certAuth = context.resources.openRawResource(R.raw.certificado_auth).use {
            certificateFactory.generateCertificate(it)
        }
        val certBlob = context.resources.openRawResource(R.raw.certificado_blob).use {
            certificateFactory.generateCertificate(it)
        }

        // Criar um KeyStore
        val keyStore =
            java.security.KeyStore.getInstance(java.security.KeyStore.getDefaultType()).apply {
                load(null, null)
                setCertificateEntry("cert_auth", certAuth)
                setCertificateEntry("cert_blob", certBlob)
            }

        // Configurar o TrustManager
        val trustManagerFactory = javax.net.ssl.TrustManagerFactory.getInstance(
            javax.net.ssl.TrustManagerFactory.getDefaultAlgorithm()
        ).apply {
            init(keyStore)
        }

        val trustManager =
            trustManagerFactory.trustManagers.first() as javax.net.ssl.X509TrustManager

        // Configurar SSLContext
        val sslContext = javax.net.ssl.SSLContext.getInstance("TLS").apply {
            init(null, arrayOf(trustManager), null)
        }

        // HostnameVerifier personalizado
        val hostnameVerifier = HostnameVerifier { hostname, _ ->
            hostname == "http://44.206.188.183:8080/api/ms-auth/" ||
                    hostname == "qualaboa.com"
        }

        // Configurar HttpLoggingInterceptor
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            android.util.Log.d("HTTP_LOG", message) // Exibe logs no Logcat
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY // Nível de logging completo
        }

        // Retornar OkHttpClient configurado
        return OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustManager)
            .hostnameVerifier(hostnameVerifier)
            .addInterceptor(loggingInterceptor) // Adiciona o interceptor de logging
            .build()
    }

    suspend fun getUserById(userId: String): UserResponse {
        return withContext(Dispatchers.IO) {
            api.getUserById(userId)
        }
    }
}
