package com.example.repository.cart

import com.example.database.MongoDatabase
import com.example.model.cart.Cart
import com.example.model.cart.CartDto
import com.example.model.product.Product
import com.example.model.product.ProductDto
import com.example.util.*
import org.bson.Document
import org.bson.types.ObjectId
import org.litote.kmongo.deleteOneById
import org.litote.kmongo.eq
import org.litote.kmongo.findOne

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

    override fun deleteItemFromBag(cartId: String): Pair<Boolean, String> {
        var doc:Document? =null

        val cartItem = cartCollection.findOne(CartDto::_id eq ObjectId(cartId))
        println("cartId: $cartId")
        println("cartItem._id: ${cartItem?.get("_id").toString()}")

        if (cartItem != null) {
            val deleteResult = cartCollection.deleteOneById(ObjectId(cartId))
            return Pair(deleteResult.wasAcknowledged(), "Cart item with ID $cartId has been deleted")
        } else {
            return Pair(false, "Cart item with ID $cartId not found")
        }
    }

    override fun deleteAllCart(): Pair<Boolean,String> {


        if(cartCollection.deleteMany(Document()).wasAcknowledged()){
        return Pair(true, "Cart has been cleared! Now it's empty!")
    } else {
        return Pair(false, "Error occured")
    }
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