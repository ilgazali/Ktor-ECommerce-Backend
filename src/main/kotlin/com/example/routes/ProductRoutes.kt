package com.example.routes

import com.example.model.product.ProductDto
import com.example.repository.product.ProductRepositoryImpl
import com.example.util.toProduct
import io.ktor.server.routing.*
import io.github.smiley4.ktorswaggerui.dsl.post
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.delete


import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

private val repo = ProductRepositoryImpl()

fun Route.productRouting(){
   route("/product"){


        post("/add",{
            tags = listOf("product")
            description = "add a single Product"

            request {
                queryParameter<String>("title") {
                    description = "title of the Product"
                }
                queryParameter<Double>("price") {
                    description = "price of the Product"

                }
                queryParameter<Int>("count") {
                    description = "count of the Product added to Cart"
                }
                queryParameter<Int>("stockCount") {
                    description = "stock amount of Product"
                }
                queryParameter<String>("category") {
                    description = "category of the Product"
                }
                queryParameter<String>("image") {
                    description = "1. image of the Product"
                }
                queryParameter<String>("description") {
                    description = "description of the Product"
                }
                queryParameter<String>("image_two") {
                    description = "2. image of the Product"
                }
                queryParameter<String>("image_three") {
                    description = "3. image of the Product"
                }
                queryParameter<Int>("sale_state"){
                    description = "represents total amount of the Products"
                }
                queryParameter<Double>("salePrice"){
                    description = "if it exists then it means sale price of the Product - attention it is nullable"
                }
                queryParameter<Double>("rating"){
                    description = "rate of the Product"
                }

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



            val title= call.request.queryParameters["title"]
            val price=call.request.queryParameters["price"]?.toDouble()
            val description=call.request.queryParameters["description"]
            val count=call.request.queryParameters["count"]?.toInt()
            val stockCount=call.request.queryParameters["stockCount"]?.toInt()
            val category=call.request.queryParameters["category"]
            val image=call.request.queryParameters["image"]
            val image_two=call.request.queryParameters["image_two"]
            val image_three= call.request.queryParameters["image_three"]
            val sale_state=call.request.queryParameters["sale_state"]?.toInt()
            val salePrice=call.request.queryParameters["salePrice"]?.toDouble()
            val rating=call.request.queryParameters["rating"]?.toDouble()

            if (title == null || count == null || category == null
                || image == null||image_two == null ||
                image_three == null || price == null ||
                description == null || stockCount == null ||
                sale_state == null || salePrice == null || rating == null) {
                call.respond(HttpStatusCode.BadRequest, "Missing or invalid parameter values")
            }else{
                val productDto = ProductDto(
                    title = title,
                    price = price,
                    description = description,
                    count = count,
                    stockCount = stockCount,
                    category = category,
                    image = image,
                    image_two = image_two,
                    image_three = image_three,
                    sale_state = sale_state,
                    salePrice = salePrice,
                    rating = rating)

                val product = productDto.toProduct()
                //call.response.headers.append("My-User-Id-Header", user.id.toString())
                val result = repo.addProduct(product)
                call.respond(HttpStatusCode.Created,result)
            }






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
        delete("/deleteAll",{
            tags = listOf("product")
            description = "delete All Products"
        }) {
           repo.deleteAll()
            call.respond(HttpStatusCode.Created,"All data was deleted")
        }


    }

}