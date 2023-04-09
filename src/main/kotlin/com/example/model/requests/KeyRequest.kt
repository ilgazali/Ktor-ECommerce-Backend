package com.example.model.requests

import com.example.model.product.ProductDto
import kotlinx.serialization.Serializable

@Serializable
data class KeyRequest(val key:String, val userId: String, val amount:Double)
