package com.example.wavefinder.model

import com.google.gson.annotations.SerializedName

data class ThumbnailsDetails(
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int
)
