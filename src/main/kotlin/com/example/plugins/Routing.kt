package com.example.plugins

import com.example.routes.getAll
import com.example.routes.login
import com.example.routes.productRouting
import com.example.routes.register
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import kotlinx.html.HTML


fun Application.configureRouting() {
    routing {
        getAll()
        get("/"){
            call.respondText("Hello World")
        }
      //  get("/register") { call.respondHtml { register_() } }
        register()
       // get("/login") { call.respondHtml { login_() } }
        login()
        productRouting()
    }
}
