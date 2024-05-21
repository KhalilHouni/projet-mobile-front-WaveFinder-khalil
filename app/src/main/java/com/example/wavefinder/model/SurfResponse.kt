package com.example.wavefinder.model

import com.google.gson.annotations.SerializedName

class SurfResponse {
    data class Photo(
        val id: String?,
        val width: Int?,
        val height: Int?,
        val url: String?,
        val filename: String?,
        val size: Int?,
        val type: String?,
        val thumbnails: Thumbnails?
    )

    data class Thumbnails(
        val small: ThumbnailDetails?,
        val large: ThumbnailDetails?,
        val full: ThumbnailDetails?
    )

    data class ThumbnailDetails(
        val url: String?,
        val width: Int?,
        val height: Int?
    )

    data class Fields(
        @SerializedName("Influencers")
        val influencers: List<String>?,
        @SerializedName("Peak Surf Season Ends")
        val peakSurfSeasonEnds: String?,
        @SerializedName("Difficulty Level")
        val difficultyLevel: Int?,
        @SerializedName("Destination")
        val destination: String?,
        @SerializedName("Geocode")
        val geocode: String?,
        @SerializedName("Surf Break")
        val surfBreak: List<String>?,
        @SerializedName("Magic Seaweed Link")
        val magicSeaweedLink: String?,
        @SerializedName("Photos")
        val photos: List<Photo>?,
        @SerializedName("Peak Surf Season Begins")
        val peakSurfSeasonBegins: String?,
        @SerializedName("Destination State/Country")
        val destinationStateCountry: String?,
        @SerializedName("Address")
        val address: String?
    )

    data class Record(
        val id: String?,
        val createdTime: String?,
        val fields: Fields?
    )

    data class SurfSpotsResponse(
        val records: List<Record>?
    )
}
