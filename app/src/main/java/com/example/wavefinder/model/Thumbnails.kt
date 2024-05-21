package com.example.wavefinder.model

import com.google.gson.annotations.SerializedName

data class Thumbnails(
    @SerializedName("small")
    val small: ThumbnailsDetails,
    @SerializedName("large")
    val large: ThumbnailsDetails,
    @SerializedName("full")
    val full: ThumbnailsDetails
)
