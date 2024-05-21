package com.example.wavefinder.apiservice



import com.example.wavefinder.model.SurfResponse
import retrofit2.http.GET


interface ApiService {
    @GET("api/spots")
    suspend fun getSurfSpots(): List<SurfResponse.Record>
}