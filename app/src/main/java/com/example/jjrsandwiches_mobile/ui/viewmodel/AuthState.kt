package com.example.jjrsandwiches_mobile.ui.viewmodel

import com.example.jjrsandwiches_mobile.data.model.User

sealed class AuthState {
    object SignedOut : AuthState()
    object Loading : AuthState()
    data class SignedIn(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}