package com.example.repository.product

import com.example.model.Product
import com.example.model.dto.ProductDto

interface ProductRepository {
    fun addProduct(product: Product):Boolean
    fun deleteAll() : Boolean
    fun getProducts():ArrayList<ProductDto>
    fun getProductByUser():ArrayList<ProductDto>
}