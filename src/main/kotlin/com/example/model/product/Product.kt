package com.example.model.product

import kotlinx.serialization.Contextual
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

 class Product(
   //  @Contextual
     @BsonId
     var _id: Id<Product>? = null,
     val title: String,
     val price: Double,
     val description: String,
     val count:Int,//sepete kac adet urun eklendi
     val stockCount:Int,//totalStock
     val category: String,
     val image: String,
     val image_two: String,
     val image_three: String,
     val sale_state:Int?, //kac adet
     val salePrice: Double?,
     val onSale:Boolean? = false,
     val rating: Double
                )

