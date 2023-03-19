package com.example.farmkat.util

import android.util.Patterns

fun validateEmail(email: String): RegisterValidation{
    if (email.isEmpty())
        return RegisterValidation.Failed("Email Can't be empty")
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return RegisterValidation.Failed("Wrong email Format")

    return RegisterValidation.Success
}

fun validatePassword(password : String) : RegisterValidation{
    if(password.isEmpty())
        return RegisterValidation.Failed("Password can't be empty")
    if(password.length < 6)
        return RegisterValidation.Failed("Password should contain 6 Char")

    return RegisterValidation.Success
}