package com.example.wavefinder.model

data class Spot(
    val records: List<SpotRecord>,
    val offset: String
)

data class SpotRecord(
    val id: String,
    val fields: SpotFields,
    val createdTime: String
)

data class SpotFields(
    val surfBreak: List<String>,
    val photos: List<SpotPhoto>,
    val address: String
)

data class SpotPhoto(
    val id: String,
    val url: String,
    val filename: String,
    val size: Int,
    val type: String,
    val thumbnails: SpotThumbnail
)

data class SpotThumbnail(
    val small: SpotThumbnailDetails,
    val large: SpotThumbnailDetails,
    val full: SpotThumbnailDetails
)

data class SpotThumbnailDetails(
    val url: String,
    val width: Int,
    val height: Int
)
