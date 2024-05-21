/*
//this class was used for step 5 with the spot.json file in the assets folder. Please keep it commented for the Api calls

// To parse the JSON, install Klaxon and do:
//
//   val welcome = Welcome.fromJson(jsonString)

package com.example.wavefinder.model

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon

private val klaxon = Klaxon()

data class Welcome (
    val records: List<Record>,
    val offset: String
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<Welcome>(json)
    }
}

data class Record (
    val id: String,
    val fields: Fields,
    val createdTime: String
)

data class Fields (
    @Json(name = "Surf Break")
    val surfBreak: List<String>,

    @Json(name = "Difficulty Level")
    val difficultyLevel: Long,

    @Json(name = "Destination")
    val destination: String,

    @Json(name = "Geocode")
    val geocode: String,

    @Json(name = "Influencers")
    val influencers: List<String>,

    @Json(name = "Magic Seaweed Link")
    val magicSeaweedLink: String,

    @Json(name = "Photos")
    val photos: List<Photo>,

    @Json(name = "Peak Surf Season Begins")
    val peakSurfSeasonBegins: String,

    @Json(name = "Destination State/Country")
    val destinationStateCountry: String,

    @Json(name = "Peak Surf Season Ends")
    val peakSurfSeasonEnds: String,

    @Json(name = "Address")
    val address: String
)

data class Photo (
    val id: String,
    val url: String,
    val filename: String,
    val size: Long,
    val type: String,
    val thumbnails: Thumbnails
)

data class Thumbnails (
    val small: Full,
    val large: Full,
    val full: Full
)

data class Full (
    val url: String,
    val width: Long,
    val height: Long
)
*/
