package com.example.qualaboaapp.ui.theme.search

import android.content.Context
import com.example.qualaboaapp.R
import com.example.qualaboaapp.ui.theme.home.categorias.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.net.ssl.HostnameVerifier

class BarRepository(val context: Context) {
    private val api = RetrofitService.retrofit
        .newBuilder()
        .client(createSecureOkHttpClient(context))
        .build()
        .create(ApiService::class.java)

    suspend fun searchBars(name: String): List<BarResponse> {
        return withContext(Dispatchers.IO) {
            api.searchBars(mapOf("name" to name))
        }
    }

    // Função para criar OkHttpClient seguro
    fun createSecureOkHttpClient(context: Context): okhttp3.OkHttpClient {
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
        val hostnameVerifier = HostnameVerifier { hostname, session ->
            // Aceitar o hostname alternativo
            hostname == "ec2-107-23-82-242.compute-1.amazonaws.com" ||
                    hostname == "qualaboa.com"
        }

        // Retornar OkHttpClient configurado
        return okhttp3.OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustManager)
            .hostnameVerifier(hostnameVerifier)
            .build()
    }
}

