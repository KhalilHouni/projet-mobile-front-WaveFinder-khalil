package com.example.wavefinder.model

import com.google.gson.annotations.SerializedName

data class Spot(
    @SerializedName("id") val id: String,
    @SerializedName("surfBreak") val surfBreak: List<String>,
    @SerializedName("address") val address: String,
    @SerializedName("photos") val photos: List<Photo>
)


