package com.example.model.cart

import com.example.model.product.Product
import com.example.model.product.ProductDto
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class CartDto(
    var _id: ObjectId? = null,
    val userId: String,
    @Serializable
    val products: ArrayList<ProductDto>
)