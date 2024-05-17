package com.example.wavefinder.model

import com.google.gson.annotations.SerializedName

data class SurfResponse(
    @SerializedName("records") val records: List<Record>
) {
    data class Record(
        @SerializedName("id") val id: String,
        @SerializedName("fields") val fields: SpotFields
    )

    data class SpotFields(
        @SerializedName("Surf Break") val surfBreak: List<String>,
        @SerializedName("Destination") val destination: String,
        @SerializedName("Photos") val photos: List<Photo>,
        @SerializedName("Address") val address: String
    )
}