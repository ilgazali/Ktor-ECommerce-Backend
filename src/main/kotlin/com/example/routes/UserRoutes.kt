package com.example.routes

import com.example.model.requests.AuthRequest
import com.example.model.responses.AuthResponse
import com.example.model.user.UserDto
import com.example.repository.user.UserRepository
import com.example.security.hashing.HashingService
import com.example.security.hashing.SaltedHash
import com.example.security.token.TokenClaim
import com.example.security.token.TokenConfig
import com.example.security.token.TokenService
import com.example.util.toUser
import io.ktor.http.*
import io.ktor.server.application.*
import io.github.smiley4.ktorswaggerui.dsl.post
import io.github.smiley4.ktorswaggerui.dsl.get

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.apache.commons.codec.digest.DigestUtils


fun Route.register(
    hashingService: HashingService,
    userRepository: UserRepository
) {
    post("signup",{
        tags = listOf("users")
        description = "Sign Up User - DO NOT ENTER ANY VALUE FOR 'ID' AND 'SALT' PARAMETERS. THEY ARE GENERATED AUTOMATICALLY!"
        request {

            body<UserDto>()
        }
        response {
            HttpStatusCode.OK to {
                description = "sign up succesfully!"
                body<UserDto> { description = "The info of user" }
            }
            HttpStatusCode.BadRequest to {
                description = "invalid fields are provided"
            }
        }
    }) {

        val request = call.receive<UserDto>()

        val firstName = request.firstName
        val lastName = request.lastName
        val email = request.email
        val username = request.username
        val password = request.password

        val areFieldsBlank = username.isBlank() || password.isBlank()
                || firstName.isBlank() || lastName.isBlank() || email.isBlank()

        val isPwTooShort = password.length < 8

        if(areFieldsBlank || isPwTooShort) {
            println("alanlar bos")
            val response = mapOf("success" to false,"message" to "invalid fields are provided")
            call.respond(response)

            return@post
        }

        val saltedHash = hashingService.generateSaltedHash(password)
        val user = UserDto(
            email = email,
            firstName= firstName,
            lastName = lastName,
            username = username,
            password = saltedHash.hash,
            salt = saltedHash.salt
        )
        val wasAcknowledged = userRepository.insertUser(user.toUser())
        if(!wasAcknowledged)  {
            println("veri tabanina kaydedilmedi")

            val response = mapOf("success" to false,"message" to "MongoDB sign up operation has failed")
            call.respond(response)
            return@post
        }

        val reponse = mapOf("success" to true,"message" to "Signing up is succesfully!")
        call.respond(reponse)
    }
}

fun Route.signIn(
    userRepository: UserRepository,
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    post("signin", {
        tags = listOf("users")
        description = "sign in user"
        request {
            body<AuthRequest>()
        }
        response {
            HttpStatusCode.OK to {
                description = "Successfully logged in!"
            }
            HttpStatusCode.Conflict to {
                description = "An invalid password or username"
            }
        }
    }
    ) {

        val request = call.receive<AuthRequest>()
        val user = userRepository.getUserByUsername(request.username)

        if(user == null) {

            val data = mapOf("data" to null)
            val response = mapOf("success" to false,"message" to "Incorrect username or password", "data" to data)
            call.respond(HttpStatusCode.Conflict,response)
            return@post
        }

        val isValidPassword = hashingService.verify(
            value = request.password,
            saltedHash = SaltedHash(
                hash = user.password,
                salt = user.salt!!
            )
        )
        if(!isValidPassword) {
            println("Entered hash: ${DigestUtils.sha256Hex("${user.salt}${request.password}")}, Hashed PW: ${user.password}")
            val data = mapOf("data" to null)
            val response = mapOf("success" to false,"message" to "Incorrect username or password", "data" to data)
            call.respond(response)

            return@post
        }

        val token = tokenService.generate(
            config = tokenConfig,
            TokenClaim(
                name = "userId",
                value = user._id.toString()
            )
        )

        val response = mapOf("success" to true,"message" to "Successfully logged in!", "data" to AuthResponse(token = token))

        call.respond(HttpStatusCode.OK,response)
    }
}

fun Route.authenticate() {
    authenticate {
        get("authenticate",{
            tags = listOf("users")
            description = "it is for check whether user is authenticated or authorized"
        }) {
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun Route.getSecretInfo() {
    authenticate {
        get("secret",{
            tags = listOf("users")
        }) {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            call.respond(HttpStatusCode.OK, "Your userId is $userId")
        }
    }
}


/*fun Route.register() {
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
}*/
