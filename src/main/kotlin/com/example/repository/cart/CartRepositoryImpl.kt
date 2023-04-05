package com.example.repository.cart

import com.example.database.MongoDatabase
import com.example.model.cart.CartDto
import com.example.model.product.Product
import com.example.model.product.ProductDto
import com.example.util.*
import org.bson.Document
import org.litote.kmongo.eq

class CartRepositoryImpl :CartRepository {
    private val cartCollection = MongoDatabase.database.getCollection("cart")

    override fun addToCart(userId: String, products:ArrayList<Product>) : Boolean {
        // Convert CartProduct list to Document list
      val newList = ArrayList<ProductDto>()
         products.forEach {
             newList.add(it.toProductDto())
        }
        val cart = CartDto(userId = userId, products = newList)
        val doc =cartToDocument(cart)

        // Insert Cart document into the database
        val result = cartCollection.insertOne(doc)

        // Return true if the insertion was successful, false otherwise
        return result.wasAcknowledged()
    }

    override fun getCartProductByUser(userId: String): ArrayList<ProductDto> {
        var list = ArrayList<ProductDto>()
        cartCollection.find(CartDto::userId eq userId).map {
                 documentToCart(it)
        }.forEach {
            it.products.forEach {
                list.add(it)
            }
        }
        return list
    }

    fun cartToDocument(cartDto: CartDto): Document {

        return Document().append("userId",cartDto.userId).append("products",cartDto.products.map { it.toProduct().productToDocument() })
    }

    fun documentToCart(document: Document): CartDto {
        val productList = ArrayList<ProductDto>()
        val userId = document.getString("userId")
        val products = document.getList("products", Document::class.java)?.map { it.documentToProductDto() }
        products?.forEach {

            productList.add(it)


        }

        return CartDto(userId = userId, products =  productList)
    }




}