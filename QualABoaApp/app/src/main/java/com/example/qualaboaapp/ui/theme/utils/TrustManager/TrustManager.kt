package com.example.qualaboaapp.ui.theme.utils.TrustManager

import android.content.Context
import com.example.qualaboaapp.R
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

fun getSslContext(context: Context): SSLContext {
    val certificateFactory = CertificateFactory.getInstance("X.509")

    // Carregar os certificados
    val blobCertificateInput: InputStream = context.resources.openRawResource(R.raw.certificado_blob)
    val authCertificateInput: InputStream = context.resources.openRawResource(R.raw.certificado_auth)

    val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply { load(null) }
    keyStore.setCertificateEntry("blob", certificateFactory.generateCertificate(blobCertificateInput))
    keyStore.setCertificateEntry("auth", certificateFactory.generateCertificate(authCertificateInput))

    blobCertificateInput.close()
    authCertificateInput.close()

    // Configurar o TrustManager
    val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).apply {
        init(keyStore)
    }

    return SSLContext.getInstance("TLS").apply {
        init(null, trustManagerFactory.trustManagers, null)
    }
}
