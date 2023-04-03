package com.example.plugins

//import com.example.routes.getAll
//import com.example.routes.login
import com.example.repository.user.UserRepository
import com.example.routes.*
import com.example.security.hashing.HashingService
import com.example.security.token.TokenConfig
import com.example.security.token.TokenService
//import com.example.routes.register
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import kotlinx.html.HTML


fun Application.configureRouting(
    userRepository: UserRepository,
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    routing {
     //   getAll()
        register(hashingService, userRepository)
        signIn(userRepository, hashingService, tokenService, tokenConfig)
        authenticate()
        getSecretInfo()
        //register()
       // login()
        productRouting()
    }
}
