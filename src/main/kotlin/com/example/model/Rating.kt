package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Rating(val rate:Double, val count: Int) {
}