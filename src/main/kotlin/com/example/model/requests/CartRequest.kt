package com.example.model.requests

import com.example.model.product.Product
import kotlinx.serialization.Serializable

@Serializable
class CartRequest(
    val userId: String,
    val product: Product
)
