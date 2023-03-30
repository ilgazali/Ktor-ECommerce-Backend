package com.example.routes

import com.example.model.dto.ProductDto
import com.example.model.dto.UserDto
import com.example.repository.product.ProductRepositoryImpl
import com.example.util.toProduct
import com.example.util.toUser
import io.ktor.client.request.*
import io.ktor.server.routing.*
import io.github.smiley4.ktorswaggerui.dsl.post
import io.github.smiley4.ktorswaggerui.dsl.get

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

private val repo = ProductRepositoryImpl()

fun Route.productRouting(){
    route("/product"){
        post("/add",{
            tags = listOf("product")
            description = "add a single Product"
            request {
                pathParameter<String>("title") {
                    description = "title of the Product"
                }
                pathParameter<Double>("price") {
                    description = "price of the Product"
                }
                pathParameter<Int>("count") {
                    description = "count of the Product"
                }
                pathParameter<String>("category") {
                    description = "category of the Product"
                }
                pathParameter<String>("image") {
                    description = "1. image of the Product"
                }
                pathParameter<String>("image_two") {
                    description = "2. image of the Product"
                }
                pathParameter<String>("image_three") {
                    description = "3. image of the Product"
                }
                pathParameter<Int>("sale_state"){
                    description = "represents total amount of the Products"
                }
                pathParameter<Double>("salePrice"){
                    description = "if it exists then it means sale price of the Product - attention it is nullable"
                }
                pathParameter<Double>("raiting"){
                    description = "rate of the Product"
                }
                body<ProductDto>() // important part
            }
            response {
                HttpStatusCode.OK to {
                    description = "the product was added successfully"
                    body<ProductDto> { description = "The result of the product" }
                }
                HttpStatusCode.BadRequest to {
                    description = "An invalid fields was provided"
                }
            }
        }) {
            val request = call.receive<ProductDto>()
            val product = request.toProduct()

            //call.response.headers.append("My-User-Id-Header", user.id.toString())

               val result = repo.addProduct(product)
               call.respond(HttpStatusCode.Created,result)

        }

        get("getAll",{
            tags = listOf("product")
            description = "get All Products"

        }) {
            val result = repo.getProducts()
            if (result.size < 1){

                call.respond(HttpStatusCode.Created, "No product here yet")

            }
            call.respond(result)

        }


    }

}