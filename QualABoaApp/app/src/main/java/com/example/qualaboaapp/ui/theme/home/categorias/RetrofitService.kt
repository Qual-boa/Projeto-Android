package com.example.qualaboaapp.ui.theme.home.categorias

import android.content.Context
import com.example.qualaboaapp.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private const val BASE_URL = "http://44.206.188.183:8080/api/ms-auth/"

    fun provideRetrofit(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createSecureOkHttpClient(context: Context): OkHttpClient {
        // (Reutilize o método `createSecureOkHttpClient` já implementado)
        val certificateFactory = java.security.cert.CertificateFactory.getInstance("X.509")

        // Carregar certificados
        val certAuth = context.resources.openRawResource(R.raw.certificado_auth).use {
            certificateFactory.generateCertificate(it)
        }
        val certBlob = context.resources.openRawResource(R.raw.certificado_blob).use {
            certificateFactory.generateCertificate(it)
        }
        val certNovo = context.resources.openRawResource(R.raw.certificado_26_11).use {
            certificateFactory.generateCertificate(it)
        }
        val certSprint = context.resources.openRawResource(R.raw.qualaboa_servebeer).use {
            certificateFactory.generateCertificate(it)
        }

        // Criar um KeyStore
        val keyStore = java.security.KeyStore.getInstance(java.security.KeyStore.getDefaultType()).apply {
            load(null, null)
            setCertificateEntry("cert_auth", certAuth)
            setCertificateEntry("cert_blob", certBlob)
            setCertificateEntry("cert_novo", certNovo)
            setCertificateEntry("qualaboa_servebeer.cer", certSprint)
        }

        // Configurar o TrustManager
        val trustManagerFactory = javax.net.ssl.TrustManagerFactory.getInstance(
            javax.net.ssl.TrustManagerFactory.getDefaultAlgorithm()
        ).apply {
            init(keyStore)
        }

        val trustManager = trustManagerFactory.trustManagers.first() as javax.net.ssl.X509TrustManager

        // Configurar SSLContext
        val sslContext = javax.net.ssl.SSLContext.getInstance("TLS").apply {
            init(null, arrayOf(trustManager), null)
        }

        // Configurar o HostnameVerifier
        val hostnameVerifier = javax.net.ssl.HostnameVerifier { hostname, session ->
            hostname == "ec2-34-235-31-164.compute-1.amazonaws.com" || hostname == "qualaboa.com" || hostname == "https://qualaboa.servebeer.com/"
        }

        // Retornar OkHttpClient configurado
        return OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustManager)
            .hostnameVerifier(hostnameVerifier)
            .build()
    }
}
