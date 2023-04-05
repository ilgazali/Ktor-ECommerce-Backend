package com.example.model.cart

import com.example.model.product.Product
import com.example.model.product.ProductDto
import kotlinx.serialization.Serializable

@Serializable
data class CartDto(
    var _id: String? = null,
    val userId: String,
    val products: ArrayList<ProductDto>
)