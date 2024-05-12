package com.example.wavefinder.model

import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    val id: String,
    val width: Int,
    val height: Int,
    val url: String,
    val filename: String,
    val size: Int,
    val type: String,
    val thumbnails: Thumbnails
) {
}