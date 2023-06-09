package com.example.routes

import com.example.model.product.ProductDto
import com.example.model.requests.CategoryRequest
import com.example.model.requests.KeyRequest
import com.example.model.requests.SearchRequest
import com.example.repository.category.CategoryRepositoryImpl
import com.example.repository.product.ProductRepositoryImpl
import com.example.util.Constants
import com.example.util.toProduct
import com.stripe.Stripe
import com.stripe.exception.StripeException
import com.stripe.model.Charge
import com.stripe.param.ChargeCreateParams
import io.ktor.server.routing.*
import io.github.smiley4.ktorswaggerui.dsl.post
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.delete


import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.bson.types.ObjectId

private val productRepository = ProductRepositoryImpl()
private val categoryRepository = CategoryRepositoryImpl()
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
                queryParameter<Boolean>("onSale"){
                    description = "sale state type of boolean of the Product"
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
            val onSale=call.request.queryParameters["onSale"]?.toBoolean()


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
                    onSale = onSale,
                    rating = rating)

                val product = productDto.toProduct()
                //call.response.headers.append("My-User-Id-Header", user.id.toString())
                val result = productRepository.addProduct(product)
                call.respond(HttpStatusCode.Created,result)
            }






        }


       authenticate {
           get("getAllProducts", {
               tags = listOf("product")
               description = "get All Products"

           }) {
               val result = productRepository.getProducts()
               if (result.size < 1) {

                   call.respond(HttpStatusCode.Created, "No product here yet")

               }
               call.respond(result)

           }
       }
       authenticate {
           get("getProductById", {
               tags = listOf("product")
               description = "get One Product"

           }) {
              val id = call.receive<String>()
               val (success,product) = productRepository.getProductById(id)

               if (success){
                   call.respond(product!!)
               }else{
                   call.respond(HttpStatusCode.NotFound,"User Not Found!")
               }



           }
       }

       authenticate {
           get("getAllProductsOnSale", {
               tags = listOf("product")
               description = "get All Products On Sale"

           }) {
               val result = productRepository.getOnSaleProducts()
               if (result.size < 1) {

                   call.respond(HttpStatusCode.Created, "No product on sale here yet")

               }
               call.respond(result)

           }
       }
       authenticate {
           get("getProductsByCategory/{category}",{
               tags = listOf("product")
               description = "get Products By Category"
               request {
                   body<CategoryRequest>()
               }
           }
           ){
               val request = call.receive<CategoryRequest>()

               val productsByCategory = productRepository.getProductsByCategory(request.category)

               if(productsByCategory.size < 1){
                   call.respond(HttpStatusCode.Conflict,"No product found in this category here")
               }else{
                   call.respond(HttpStatusCode.OK,productsByCategory)
               }
           }
       }

       authenticate {
           post("category/{category}",{
               tags = listOf("category")
               description = "add Category For Products"
           }) {
               val category = call.receive<String>()
               val result = categoryRepository.addCategory(category)

               if (result){
                   call.respond(HttpStatusCode.Created,"added category")
               }else{
                   call.respond(HttpStatusCode.BadRequest,"Something went wrong!")
               }
           }
       }

       authenticate {
           get("categories",{
               tags = listOf("category")
               description = "get All Categories"
           }) {
               val result = categoryRepository.getCategories()
              if (result.size < 1){
                   call.respond(HttpStatusCode.Conflict,"No category found!")
               }else{
                  call.respond(HttpStatusCode.OK,result)
               }
           }
       }




        delete("/deleteAll",{
            tags = listOf("product")
            description = "delete All Products"
        }) {
           productRepository.deleteAll()
            call.respond(HttpStatusCode.Created,"All data was deleted")
        }

       authenticate {
           get("/{search}",{
               tags = listOf("product")
               description = "search any product, it returns the most related data(product, category...) over the search parameter provided"
               request {
                   body<SearchRequest>()
               }
           }) {
               val search = call.receive<SearchRequest>()
               val products = productRepository.searchProducts(search.search)
               val data = mapOf(Constants.MESSAGE to "No product found by the search")
               if(products.isEmpty()){
                   call.respond(HttpStatusCode.NotFound,data)
               }else{
                   call.respond(HttpStatusCode.OK,products)
               }
           }
       }




    }

}