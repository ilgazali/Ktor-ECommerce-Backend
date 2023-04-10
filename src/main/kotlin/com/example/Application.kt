package com.example

import com.example.database.MongoDatabase
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import com.example.repository.user.UserRepositoryImpl
import com.example.security.hashing.SHA256HashingService
import com.example.security.token.JwtTokenService
import com.example.security.token.TokenConfig



/*fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}*/
fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)
fun Application.module() {

    MongoDatabase.init()

    val userRepository = UserRepositoryImpl()
    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )
    val hashingService = SHA256HashingService()

    configureSerialization()
    configureSwagger()
    configureSecurity(tokenConfig)
    configureRouting(userRepository, hashingService, tokenService, tokenConfig)
    configureCORS()

}
