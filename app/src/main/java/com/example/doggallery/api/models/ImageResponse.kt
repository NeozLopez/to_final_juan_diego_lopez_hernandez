package com.example.doggallery.api.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter=true)
data class ImageResponse(
    @field:Json(name="message") val message:String,
    @field:Json(name="status") val status: String
)