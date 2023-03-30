package com.example.routes

import com.example.model.dto.UserDto

import com.example.repository.user.UserRepositoryImpl
import com.example.service.AuthenticationService
import com.example.util.toUser
import io.github.smiley4.ktorswaggerui.dsl.post
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


private val authenticationService: AuthenticationService = AuthenticationService(UserRepositoryImpl())

private val repo = UserRepositoryImpl()

fun Route.register() {
   route("/register"){


       post({
           tags = listOf("user")
           description = "provide registration and login "
           request {

               pathParameter<String>("firstName") {
                   description = "the math operation to perform. Either 'add' or 'sub'"
               }
               pathParameter<String>("lastName") {
                   description = "the math operation to perform. Either 'add' or 'sub'"
               }
               pathParameter<String>("email") {
                   description = "the math operation to perform. Either 'add' or 'sub'"
               }
               pathParameter<String>("password") {
                   description = "the math operation to perform. Either 'add' or 'sub'"
               }

               body<UserDto>() // important part
           }

            response {
               HttpStatusCode.OK to {
                   description = "The registration was successful"
                   body<UserDto> { description = "The result of the registration" }
               }
               HttpStatusCode.BadRequest to {
                   description = "An invalid fields was provided"
               }
           }



       }) {


               val request = call.receive<UserDto>()
               val user = request.toUser()

           //call.response.headers.append("My-User-Id-Header", user.id.toString())

           authenticationService.register(user).let {
               call.response.headers.append("My-User-Id-Header", user._id.toString())
               call.respond(HttpStatusCode.Created)
            }

       }
   }
}

fun Route.getAll(){
    route("/getAll"){
        get {
           val c = repo.getUsers()
           call.respond(c)
        }
    }
}

fun Route.login() {
    route("/login"){

        post {

            val params = call.receiveParameters()
            val email = params["email"] ?: ""
            val password = params["password"] ?: ""
            val user = authenticationService.login(email, password)

            if (user != null) {

                call.respondText("User logged in", status = HttpStatusCode.Created)
            } else {
                call.respondText("User login error", status = HttpStatusCode.Created)
            }
        }

    }
}
