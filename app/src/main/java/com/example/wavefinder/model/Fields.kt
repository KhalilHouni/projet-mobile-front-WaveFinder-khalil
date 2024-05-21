package com.example.wavefinder.model

import kotlinx.serialization.Serializable

@Serializable
data class Fields(
    val influencers: List<String>,
    val peakSurfSeasonEnds: String,
    val difficultyLevel: Int,
    val destination: String,
    val geocode: String,
    val surfBreak: List<String>,
    val magicSeaweedLink: String,
    val peakSurfSeasonBegins: String,
    val destinationStateCountry: String,
    val address: String
)

