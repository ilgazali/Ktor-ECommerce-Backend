package com.example.model.cart

import com.example.model.product.Product
import com.example.model.product.ProductDto
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id

data class Cart(
    @Contextual
    var _id: Id<Cart>? = null,
    val userId: String,
   // val date: String,
    val products: ArrayList<ProductDto>
)