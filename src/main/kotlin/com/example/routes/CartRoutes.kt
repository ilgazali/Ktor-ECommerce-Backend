package com.example.routes

import com.example.model.cart.CartDto
import com.example.model.product.Product
import com.example.model.requests.CartRequest
import com.example.model.requests.UserRequest
import com.example.repository.cart.CartRepositoryImpl
import com.example.util.Constants
import com.example.util.toCart
import com.example.util.toProduct
import io.github.smiley4.ktorswaggerui.dsl.post
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.delete

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
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
                    call.respond(HttpStatusCode.Created, mapOf(Constants.STATUS to true,Constants.MESSAGE to "Cart added successfully"))
                } else {
                    call.respond(HttpStatusCode.InternalServerError, mapOf(Constants.STATUS to false,Constants.MESSAGE to "Failed to add cart"))
                }
            }

            get("getCartProductByUser/{userId}",{
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
            delete("/delete/{cartId}",{
                tags = listOf("cart")
                request {
                    pathParameter<String>("cartId") {
                        description = "cardId like 642cb564c7282327e89d7afd"
                    }
                    body<CartRequest>()
                }

            }) {
                val id = call.receive<CartRequest>()
                val (success, message) = cartRepository.deleteItemFromBag(id.cartId)

                val data = mapOf(Constants.STATUS to success,Constants.MESSAGE to message)


                if (success) {
                    call.respond(HttpStatusCode.OK,data)
                } else {
                    call.respond(HttpStatusCode.NotFound, data)
                }

            }

            authenticate {
                delete ("/delete/all",{
                    tags = listOf("cart")
                    description = "it clears cart."
                }){
                    val (success, message) = cartRepository.deleteAllCart()

                    val data = mapOf(Constants.STATUS to success,Constants.MESSAGE to message)


                    if (success) {
                        call.respond(HttpStatusCode.OK,data)
                    } else {
                        call.respond(HttpStatusCode.NotFound, data)
                    }
                }
            }




        }
}
