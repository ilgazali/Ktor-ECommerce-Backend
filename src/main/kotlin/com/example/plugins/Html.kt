package com.example.plugins


import kotlinx.html.*



fun  HTML.login_() {
    head {
        title("My E-Commerce App")
    }
    body {
        form("/login", method = FormMethod.post) {
            label {
                htmlFor = "email"
                +"Email:"
            }
            input {
                type = InputType.email
                name = "email"
            }
            br
            label {
                htmlFor = "password"
                +"Password:"
            }
            input {
                type = InputType.password
                name = "password"
            }
            br
            button {
                type = ButtonType.submit
                +"Log in"
            }
        }

    }
}

fun HTML.register_() {
    head {
        title("My E-Commerce App")
    }
    body {
        form(method = FormMethod.post,action = "/register") {
            label {
                htmlFor = "id"
                +"id:"
            }
            input {
                type = InputType.text
                name = "id"
            }
            br
            label {
                htmlFor = "firstName"
                +"firstName:"
            }
            input {
                type = InputType.text
                name = "firstName"
            }
            br
            label {
                htmlFor = "lastName"
                +"lastName:"
            }
            input {
                type = InputType.text
                name = "lastName"
            }
            br
            label {
                htmlFor = "email"
                +"Email:"
            }
            input {
                type = InputType.email
                name = "email"
            }
            br
            label {
                htmlFor = "password"
                +"Password:"
            }
            input {
                type = InputType.password
                name = "password"
            }
            br
            button {
                type = ButtonType.submit
                +"Sign up"
            }
        }

    }
}
