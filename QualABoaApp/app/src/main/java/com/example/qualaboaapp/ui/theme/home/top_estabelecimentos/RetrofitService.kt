package com.example.qualaboaapp.ui.theme.home.top_estabelecimentos

import android.content.Context
import com.example.qualaboaapp.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.HostnameVerifier


object RetrofitService {

    private const val BASE_URL = "https://ec2-107-23-82-242.compute-1.amazonaws.com/api/ms-auth/"
    private const val PHOTO_BASE_URL =
        "https://ec2-107-23-82-242.compute-1.amazonaws.com/api/ms-blob/"

    // Função para criar EstablishmentsApi
    fun provideEstablishmentsApi(context: Context): EstablishmentsApi {
        val client = createSecureOkHttpClient(context)
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EstablishmentsApi::class.java)
    }

    // Função para criar EstablishmentPhotoApi
    fun providePhotoApi(context: Context): EstablishmentPhotoApi {
        val client = createSecureOkHttpClient(context)
        return Retrofit.Builder()
            .baseUrl(PHOTO_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EstablishmentPhotoApi::class.java)
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