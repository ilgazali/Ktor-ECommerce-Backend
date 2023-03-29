package com.example.repository

import com.example.database.MongoDatabase
import com.example.model.User
import com.mongodb.client.model.Filters.eq
import org.bson.Document
import org.litote.kmongo.Id
import java.util.ArrayList

class UserRepositoryImpl : UserRepository {

    private val usersCollection = MongoDatabase.database.getCollection("users")


    override fun getUsers(): ArrayList<User> {
        val list = ArrayList<User>()

            val col = usersCollection.find().map{
                documentToUser(it)
            }.forEach {
                list.add(it)
            }
            return list
        }



    override fun getUserByEmail(email: String): User? {
        val document = usersCollection.find(eq("email", email)).first()
        return if (document != null) documentToUser(document) else null
    }


    override fun getUserById(id: String): User? {
        val document = usersCollection.find(eq("id", id)).first()
        return if (document != null) documentToUser(document) else null
    }


    override fun createUser(user: User): Boolean {
        val document = userToDocument(user)
        val check = usersCollection.insertOne(document).wasAcknowledged()
        if (check){
            return true
        }else{
            return false
        }

    }

    private fun documentToUser(document: Document): User {
        return User(
            firstName = document.getString("firstName"),
            lastName = document.getString("lastName"),
            password = document.getString("password"),
            email = document.getString("email")
        )
    }

    private fun userToDocument(user: User): Document {
        return Document()
            .append("id",user.id.toString())
            .append("firstName", user.firstName)
            .append("lastName",user.lastName)
            .append("password", user.password)
            .append("email", user.email)
    }
}
