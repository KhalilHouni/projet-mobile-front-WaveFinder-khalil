package com.example.wavefinder.model

import kotlinx.serialization.Serializable

@Serializable
data class Thumbnails(
    val small: ThumbnailsDetails,
    val large: ThumbnailsDetails,
    val full: ThumbnailsDetails
) {
}