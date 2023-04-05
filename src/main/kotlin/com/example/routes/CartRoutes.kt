package com.example.routes

import com.example.model.cart.CartDto
import com.example.model.product.Product
import com.example.model.requests.UserRequest
import com.example.repository.cart.CartRepositoryImpl
import com.example.util.toCart
import com.example.util.toProduct
import io.github.smiley4.ktorswaggerui.dsl.post
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private val cartRepository = CartRepositoryImpl()


fun Route.cartRouting(){
        route("cart"){

            post ("/addToCart",{
                tags = listOf("cart")
                request {
                    body<CartDto>()
                }
            }) {
                val request = call.receive<CartDto>()
                val cart = request.toCart()

                val userId = cart.userId
                val products = cart.products.map {
                    it.toProduct()
                }

                // Call the addToCart function to add the cart to the database
                val isAdded = cartRepository.addToCart(userId, products as ArrayList<Product>)

                if (isAdded) {
                    call.respond(HttpStatusCode.Created, "Cart added successfully")
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "Failed to add cart")
                }
            }

            post("getCartProductByUser/{userId}",{
                tags = listOf("cart")
                request {
                    body<UserRequest>()
                }
            }) {

                val request = call.receive<UserRequest>()

                val cartProductsByUser = cartRepository.getCartProductByUser(request.userId)

                if(cartProductsByUser.size < 1){
                    call.respond(HttpStatusCode.Conflict,"Empty cart!")
                }else{
                    call.respond(HttpStatusCode.OK,cartProductsByUser)
                }


            }



        }
}
