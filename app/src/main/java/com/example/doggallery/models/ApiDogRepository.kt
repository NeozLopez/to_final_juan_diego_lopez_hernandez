package com.example.doggallery.models

import com.example.doggallery.api.ApiClient
import com.example.doggallery.api.models.ImageResponse

class ApiDogRepository {
    private val apiService = ApiClient.apiService

    suspend fun getImageRandom():ImageResponse{
        return apiService.getRandomImage()
    }

    suspend fun getImageBreedRandom(raza:String):ImageResponse{
        return apiService.getRandomBreedImage(raza)
    }
}

