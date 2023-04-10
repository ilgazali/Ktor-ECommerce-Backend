package com.example.repository.user

import com.example.database.MongoDatabase
import com.example.model.user.User
import com.example.model.user.UserDto
import com.mongodb.client.model.Filters.eq
import org.bson.Document

class UserRepositoryImpl : UserRepository {



        private val usersCollection = MongoDatabase.database.getCollection("users")


        override fun getUserByUsername(username: String): UserDto? {
            val document = usersCollection.find(eq("username", username)).first()
            return if (document != null) documentToUserDto(document) else null
        }

        override fun insertUser(user: User): Boolean {
            val document = userToDocument(user)
            return usersCollection.insertOne(document).wasAcknowledged()

        }


/*
    override fun getUserByEmail(email: String): UserDto? {
        val document = usersCollection.find(eq("email", email)).first()
        return if (document != null) documentToUserDto(document) else null
    }


    override fun getUserById(id: String): UserDto? {
        val document = usersCollection.find(eq("id", id)).first()
        return if (document != null) documentToUserDto(document) else null
    }



 */


        private fun documentToUserDto(document: Document): UserDto {
            return UserDto(
                _id = document.get("_id").toString(), // to get id of object from db use this get()
                username = document.getString("username"),
                firstName = document.getString("firstName"),
                lastName = document.getString("lastName"),
                password = document.getString("password"),
                email = document.getString("email"),
                salt = document.getString("salt")

            )
        }

        private fun userToDocument(user: User): Document {
            return Document()
                //.append("_id",user._id.toString())
                .append("username", user.username)
                .append("firstName", user.firstName)
                .append("lastName", user.lastName)
                .append("password", user.password)
                .append("email", user.email)
                .append("salt", user.salt)

        }

}
