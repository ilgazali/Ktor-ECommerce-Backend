## Ktor ECommerce Backend

This repository contains the source code for the backend of an E-commerce application developed using Ktor framework, which is a framework for building asynchronous servers and clients in connected systems using Kotlin programming language.

This backend provides a RESTful API for managing products, categories, and orders. It uses MongoDB as a database, JWT authentication for user authentication, and Stripe for payment processing.

## Getting Started

To get started with this project, follow the steps below:

1. Clone the repository to your local machine using the following command: 
```sh
git clone https://github.com/ilgazali/Ktor-ECommerce-Backend.git
```
2. Install the required dependencies using the following command:
```sh
./gradlew build
```

3. Install and start a MongoDB instance on your local machine. You can follow the instructions in the MongoDB official website to install MongoDB on your operating system.

4. Edit the application.conf file located in the src/main/resources directory and update the MongoDB connection URL, database name, username, and password with your own credentials. You can use the following example as a guide:
```sh
ktor {
    application {
        modules = [ com.example.ApplicationKt.main ]
    }
    server {
        host = "0.0.0.0"
        port = 8080
    }
    dataSource {
        type = "org.jetbrains.exposed.sql.Database"
        databaseName = "ecommerce"
        driverClassName = "com.mongodb.MongoDriver"
        user = "username"
        password = "password"
        url = "mongodb://localhost:27017"
    }
}
```
5. Edit the stripe.properties file located in the src/main/resources directory and update the Stripe API keys with your own credentials.

6. Run the application using the following command:
```sh
./gradlew run
```
This will start the Ktor server on port 8080.
## API Endpoints
This backend provides the following RESTful API endpoints:

### users
- POST /signup
- POST /signin
- GET /authenticate
- GET /secret
### cart
- POST /cart/addToCart
- GET /cart/getCartProductByUser/{userId}
- DELETE /cart/delete/{cartId}
- DELETE /cart/delete/all
### product
- POST /product/add
- GET /product/getAllProducts
- GET /product/getProductById
- GET /product/getAllProductsOnSale
- GET /product/getProductsByCategory/{category}
- DELETE /product/deleteAll
- GET /product/{search}
### category
- POST /product/category/{category}
- GET /product/categories
### payment
- POST /payment/checkout

## Dependencies

### MongoDB
This project uses MongoDB as a database to store products, categories, and orders. MongoDB is a NoSQL document database that provides a flexible and scalable approach to managing data.

### JWT Authentication
This project uses JWT (JSON Web Tokens) for user authentication. JWT is a standard for securely transmitting information between parties as a JSON object. It is commonly used for authentication and authorization purposes in modern web

### Swagger
This project uses Swagger for API documentation. Swagger is an open-source tool that helps developers design, build, document, and consume RESTful web services. It provides a user-friendly interface for developers to document their APIs and enables consumers to easily discover and understand how to use them.

