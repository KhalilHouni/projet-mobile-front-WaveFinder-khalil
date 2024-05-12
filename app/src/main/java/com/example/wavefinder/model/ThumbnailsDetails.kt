package com.example.wavefinder.model

import kotlinx.serialization.Serializable

@Serializable
class ThumbnailsDetails(
    val url: String,
    val width: Int,
    val height: Int
) {
}