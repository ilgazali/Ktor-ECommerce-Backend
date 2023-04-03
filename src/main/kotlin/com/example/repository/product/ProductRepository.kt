package com.example.repository.product

import com.example.model.product.Product
import com.example.model.product.ProductDto

interface ProductRepository {
    fun addProduct(product: Product):Boolean
   fun deleteAll() : Boolean
   fun getProducts():ArrayList<ProductDto>
    fun getProductByUser():ArrayList<ProductDto>
}