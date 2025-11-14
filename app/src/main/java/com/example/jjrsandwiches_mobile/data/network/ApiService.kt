package com.example.jjrsandwiches_mobile.data.network

import com.example.jjrsandwiches_mobile.data.model.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/auth/register")
    suspend fun signUp(@Body signUpRequest: Map<String, String>): AuthResponse

    @POST("/auth/login")
    suspend fun signIn(@Body signInRequest: Map<String, String>): AuthResponse
}