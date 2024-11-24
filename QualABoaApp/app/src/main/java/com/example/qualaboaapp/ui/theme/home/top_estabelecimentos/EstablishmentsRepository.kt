package com.example.qualaboaapp.ui.theme.home.top_estabelecimentos

class EstablishmentsRepository(
    private val establishmentsApi: EstablishmentsApi,
    private val photoApi: EstablishmentPhotoApi
) {
    suspend fun fetchTopEstablishments(): List<Establishment> {
        return establishmentsApi.getEstablishments()
    }

    suspend fun fetchEstablishmentPhotos(establishmentId: String): List<String> {
        return photoApi.getEstablishmentPhotos(establishmentId)
    }
}
