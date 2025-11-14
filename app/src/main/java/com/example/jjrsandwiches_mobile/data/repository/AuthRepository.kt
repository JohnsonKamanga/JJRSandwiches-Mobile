package com.example.jjrsandwiches_mobile.data.repository

import com.example.jjrsandwiches_mobile.data.model.AuthResponse
import com.example.jjrsandwiches_mobile.data.network.ApiService
import com.example.jjrsandwiches_mobile.data.sessions.SessionManager

class AuthRepository(private val apiService: ApiService, private val sessionManager: SessionManager) {

    suspend fun signUp(email: String, password: String, firstName: String, lastName: String): AuthResponse {
        val response = apiService.signUp(mapOf(
            "email" to email,
            "password" to password,
            "firstName" to firstName,
            "lastName" to lastName
        ))
        sessionManager.saveSession(response.accessToken, response.user)
        return response
    }

    suspend fun signIn(email: String, password: String): AuthResponse {
        val response = apiService.signIn(mapOf("email" to email, "password" to password))
        sessionManager.saveSession(response.accessToken, response.user)
        return response
    }

    suspend fun signOut() {
        sessionManager.clearSession()
    }
}