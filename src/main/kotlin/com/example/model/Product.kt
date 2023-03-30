package com.example.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id

 class Product(
             @Contextual
             var _id: Id<Product>? = null,
               val title: String,
               val price: Double,
               val description: String,
               val count:Int,//sepete kac adet urun eklendi
               val category: String,
               val image: String,
               val image_two: String,
               val image_three: String,
               val sale_state:Int, //kac adet
               val salePrice: Double?,
               val rating: Double
                )

