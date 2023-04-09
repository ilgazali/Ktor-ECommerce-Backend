package com.example.repository.product

import com.example.model.product.Product
import com.example.model.product.ProductDto
import org.bson.types.ObjectId

interface ProductRepository {
    fun addProduct(product: Product):Boolean
   fun deleteAll() : Boolean
   fun getProductById(id: String) : Pair<Boolean,ProductDto?>
   fun getProducts():ArrayList<ProductDto>
    fun getOnSaleProducts():ArrayList<ProductDto>
    fun getProductsByCategory(category: String): ArrayList<ProductDto>

    fun searchProducts(query: String): List<ProductDto>

}