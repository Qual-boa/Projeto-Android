package com.example.qualaboaapp.ui.theme.search

import android.content.Context
import android.util.Log
import com.example.qualaboaapp.R
import com.example.qualaboaapp.ui.theme.establishment.AddressResponse
import com.example.qualaboaapp.ui.theme.establishment.EstablishmentResponse
import com.example.qualaboaapp.ui.theme.establishment.GeocodingApi
import com.example.qualaboaapp.ui.theme.establishment.Location
import com.example.qualaboaapp.ui.theme.home.categorias.RetrofitService
import com.example.qualaboaapp.ui.theme.home.categorias.RetrofitService.retrofit
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.net.ssl.HostnameVerifier
import com.google.gson.Gson
import okhttp3.Request
import org.json.JSONObject

open class BarRepository(val context: Context) {
    private val api = RetrofitService.retrofit
        .newBuilder()
        .client(createSecureOkHttpClient(context))
        .build()
        .create(ApiService::class.java)

    suspend fun searchBars(searchTerm: String, categories: List<Category>): List<BarResponse> {
        val requestBody = SearchRequest(
            name = searchTerm,
            categories = categories
        )

        return withContext(Dispatchers.IO) {
            api.searchBars(requestBody) // Envia o objeto diretamente
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


    suspend fun getCoordinatesFromAddress(address: String, apiKey: String): Location? {
        val geocodingUrl = "https://maps.googleapis.com/maps/api/geocode/json"
        val fullUrl = "$geocodingUrl?address=${address.replace(" ", "+")}&key=$apiKey"

        return try {
            val response = retrofit.create(GeocodingApi::class.java).getCoordinates(fullUrl)
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
}
