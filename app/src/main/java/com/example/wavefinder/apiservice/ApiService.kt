package com.example.wavefinder.apiservice

import com.example.wavefinder.model.SurfResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface ApiService {
    @GET("api/spots")
    suspend fun getSurfSpots(): List<SurfResponse.Record>

    @POST("api/spots")
    suspend fun addSurfSpot(@Body spot: SurfResponse.Record)

    @DELETE("api/spots/{id}")
    suspend fun deleteSurfSpot(@Path("id") id: String)

    @PUT("api/spots/{id}")
    suspend fun updateSurfSpot(@Path("id") id: String)
}
