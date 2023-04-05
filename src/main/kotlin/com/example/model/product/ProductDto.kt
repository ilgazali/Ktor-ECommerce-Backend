package com.example.model.product

import kotlinx.serialization.Serializable

@Serializable
class ProductDto(
    var _id: String? = null,
    val title: String,
    val price: Double,
    val description: String,
    val count:Int,//sepete kac adet urun eklendi
    val stockCount:Int,//totalStock
    val category: String,
    val image: String,
    val image_two: String,
    val image_three: String,
    val sale_state:Int?, //kac adet indirimli urun var
    val salePrice: Double?,
    val onSale:Boolean? = false,
    val rating: Double
)