package com.example.model.responses

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String
)
