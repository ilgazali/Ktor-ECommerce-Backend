package com.example.repository.cart

import com.example.model.product.Product
import com.example.model.product.ProductDto

interface CartRepository {
    fun addToCart(userId: String, products:ArrayList<Product>) : Boolean
    fun getCartProductByUser(userId:String):ArrayList<ProductDto>
}