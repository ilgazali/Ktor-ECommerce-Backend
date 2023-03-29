package com.example.database

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import io.ktor.server.config.*

object MongoDatabase {
    lateinit var database: com.mongodb.client.MongoDatabase

    fun init() {
       val connectionString = "mongodb://localhost:27017"
        val databaseName = "db"
        val client: MongoClient = MongoClients.create(connectionString)
        database = client.getDatabase(databaseName)
    }
}
