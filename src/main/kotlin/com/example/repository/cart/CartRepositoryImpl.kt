package com.example.repository.cart

import com.example.database.MongoDatabase
import com.example.model.cart.CartDto
import com.example.model.product.Product
import com.example.model.product.ProductDto
import com.example.model.requests.CartRequest
import com.example.model.user.User
import com.example.model.user.UserDto
import com.example.util.toProduct
import org.bson.Document
import org.litote.kmongo.eq

class CartRepositoryImpl :CartRepository {
    private val cartCollection = MongoDatabase.database.getCollection("cart")

    override fun addToCart(userId: String, products:ArrayList<Product>) : Boolean {
        // Convert CartProduct list to Document list
        val productList = products.map {
            productToDocument(it)
        }
        // Create Cart document
        val cartDocument = Document()
        cartDocument["userId"] = userId
        cartDocument["products"] = productList

        // Insert Cart document into the database
        val result = cartCollection.insertOne(cartDocument)

        // Return true if the insertion was successful, false otherwise
        return result.wasAcknowledged()
    }

    override fun getCartProductByUser(userId: String): ArrayList<ProductDto> {
        val list = ArrayList<ProductDto>()
        cartCollection.find(CartDto::_id eq userId).map {
                 it.get("products")!!
        }.forEach {
           val product =  it as ProductDto
            list.add(product)
        }
        return list
    }

  /*  fun cartFromDocument(document: Document): Cart {
        val id = document["_id"] as ObjectId
        val userId = document["userId"] as Int
        val date = LocalDate.parse(document["date"] as String)
        val products = (document["products"] as List<Document>).map { productFromDocument(it) }

        return Cart(id, userId, date, products)
    }*/
    private fun productToDocument(product: Product): Document {
        return Document() // BURADA ID ILE ILGILI HATA ALABILIRIM AFTER CHECKKKK
            .append("title", product.title)
            .append("price", product.price)
            .append("description", product.description)
            .append("count", product.count)
            .append("stockCount",product.stockCount)
            .append("category", product.category)
            .append("image", product.image)
            .append("image_two", product.image_two)
            .append("image_three", product.image_three)
            .append("sale_state", product.sale_state)
            .append("salePrice", product.salePrice)
            .append("onSale",product.onSale)
            .append("rating", product.rating)
    }


}