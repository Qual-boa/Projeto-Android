package com.example.qualaboaapp.ui.theme.home.top_estabelecimentos

class EstablishmentsRepository(
    private val establishmentsApi: EstablishmentsApi,
    private val photoApi: EstablishmentPhotoApi
) {
    suspend fun fetchTopEstablishments(): List<Establishment> {
        return establishmentsApi.getEstablishments()
    }

    suspend fun fetchEstablishmentPhotos(establishmentId: String): List<EstablishmentPhoto> {
        return photoApi.getEstablishmentPhotos(establishmentId).map { photo ->
            EstablishmentPhoto(
                id = photo.id,
                establishmentId = photo.establishmentId,
                establishmentCategory = photo.establishmentCategory,
                imgUrl = photo.imgUrl,
                originalFilename = photo.originalFilename
            )
        }
    }
}
