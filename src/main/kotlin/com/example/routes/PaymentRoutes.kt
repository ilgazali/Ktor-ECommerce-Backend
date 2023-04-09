package com.example.routes

import com.example.model.order.Order
import com.example.model.requests.KeyRequest
import com.example.repository.cart.CartRepositoryImpl
import com.example.repository.order.OrderRepositoryImpl
import com.stripe.Stripe
import com.stripe.exception.StripeException
import com.stripe.model.Charge
import com.stripe.param.ChargeCreateParams
import io.github.smiley4.ktorswaggerui.dsl.post
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.paymentRouting(){
    val orderRepository = OrderRepositoryImpl()

    val cartRepository = CartRepositoryImpl()
    route("/payment"){

        authenticate {

        post("/checkout",{
            tags = listOf("payment")
            request {
                body<KeyRequest>()
            }
        }) {
            // get payment token from request body
            val paymentToken = call.receive<KeyRequest>()

            // set up Stripe API key
            Stripe.apiKey = "sk_test_51KpvHVGxCwE8wgTc2rfMYDJoiHgraDLgTDXb3t3V3I28sEJRVzj3mKcZOYEEERegujvEAcjBeVIctUCPOFFziy2900wEIq9Kdo"

            // create a charge using the payment token
            val chargeParams = ChargeCreateParams.builder()
                .setAmount((paymentToken.amount * 100).toLong()) // set the amount to charge (in cents)
                .setCurrency("usd")
                .setSource(paymentToken.key)
                .setDescription("Example charge")
                .build()

            try {
                // make the charge
                val charge = Charge.create(chargeParams)
                val status = charge.status

                // check the status of the payment
                if (status == "succeeded") {
                    // firstly get cart products by user
                   val products = cartRepository.getCartProductByUser(paymentToken.userId)

                    val order = Order(userId = paymentToken.userId, products = products,
                        amount = paymentToken.amount, paymentId =charge.id, status = status, currency = "usd")
                    val isOrderDoneSuccessfully = orderRepository.order(order)

                    if (isOrderDoneSuccessfully){
                        call.respond( call.respond("message" to "payment ${charge.status}"))
                    }

                } else if (status == "pending") {
                    // payment is still pending
                    call.respond("message" to "payment is still pending")
                } else if (status == "failed") {
                    // payment failed
                    call.respond("message" to "payment failed")
                }


            } catch (e: StripeException) {
                // return an error message to the client
                call.respondText("Error: ${e.message}")
            }
        }

        }
    }

}