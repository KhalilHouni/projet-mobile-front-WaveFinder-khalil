package com.example.wavefinder.apiservice

import com.example.wavefinder.model.SurfResponse
import retrofit2.http.GET

interface ApiService {
    @GET("appMwQ5MfHUdy5N5C/Surf%20Destinations")
    suspend fun getSurfSpots(): SurfResponse.SurfSpotsResponse
}