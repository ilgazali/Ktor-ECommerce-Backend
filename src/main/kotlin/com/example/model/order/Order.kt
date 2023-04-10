package com.example.model.order


import com.example.model.product.ProductDto
import org.litote.kmongo.Id
import java.time.LocalDateTime

data class Order(
    var _id: Id<Order>? = null,
     val userId: String,
     val products : List<ProductDto>,
     val amount: Double,
     val currency: String,
     val paymentId: String?=null,
     val createdAt: LocalDateTime = LocalDateTime.now(),
     val status :String?=null)