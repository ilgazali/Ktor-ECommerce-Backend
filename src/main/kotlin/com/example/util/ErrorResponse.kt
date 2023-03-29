package com.example.util

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(val message: String) {
    companion object {
        val NOT_FOUND_RESPONSE = ErrorResponse("User was not found")
        val BAD_REQUEST_RESPONSE = ErrorResponse("Invalid request")
    }
}