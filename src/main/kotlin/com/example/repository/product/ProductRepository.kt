package com.example.repository.product

import com.example.model.product.Product
import com.example.model.product.ProductDto
import org.bson.types.ObjectId

interface ProductRepository {
    fun addProduct(product: Product):Boolean
   fun deleteAll() : Boolean
   fun getProductById(id: String) : Pair<Boolean,ProductDto?>
   fun getProducts():List<ProductDto>
    fun getOnSaleProducts():List<ProductDto>
    fun getProductsByCategory(category: String): List<ProductDto>

    fun searchProducts(query: String): List<ProductDto>

}