package com.example.plugins

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.ktor.server.application.*


fun Application.configureSwagger() {
    install(SwaggerUI) {
        swagger {
            swaggerUrl = "swagger-ui"
            forwardRoot = true
        }
        info {
            title = "Sample API"
            version = "1.0"
            description = "Swagger for Sample API"
        }
    }
}
