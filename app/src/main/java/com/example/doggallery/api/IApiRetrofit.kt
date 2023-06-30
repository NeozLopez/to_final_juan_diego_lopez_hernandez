package com.example.doggallery.api

import com.example.doggallery.api.models.ImageResponse
import com.squareup.moshi.JsonClass
import retrofit2.http.GET
import retrofit2.http.Path

@JsonClass(generateAdapter = true)
interface IApiRetrofit {
    @GET(ApiConstants.RANDOM_IMAGE)
    suspend fun getRandomImage(): ImageResponse

    @GET(ApiConstants.RANDOM_BREED_IMAGE)
    suspend fun getRandomBreedImage(@Path("raza") raza:String): ImageResponse
}