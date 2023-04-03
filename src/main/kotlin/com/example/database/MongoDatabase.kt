package com.example.database

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import io.ktor.server.config.*

import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

object MongoDatabase {
    lateinit var database: com.mongodb.client.MongoDatabase

    fun init() {

        val mongoPw = System.getenv("MONGO_PW")
        val connectionString = "mongodb+srv://ilgazalii:$mongoPw@cluster0.wszoteo.mongodb.net/ktor?retryWrites=true&w=majority"
        val databaseName = "ktor"
        val client : MongoClient = MongoClients.create(connectionString)
        database = client.getDatabase(databaseName)
    }
/*    fun init() {
       val connectionString = "mongodb://localhost:27017"
        val databaseName = "db"
        val client: MongoClient = MongoClients.create(connectionString)
        database = client.getDatabase(databaseName)
    }*/
}
