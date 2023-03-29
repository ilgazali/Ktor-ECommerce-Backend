package com.example.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
 class Product(val id: Int,

                   val title: String,

                   val price: Double,

                   val description: String,

                   val category: String,

                   val image: String,

                   val rating: Rating
                ){

}