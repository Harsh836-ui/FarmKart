package com.example.farmkat.data

data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    var imagePath: String = "" //since at starting there's no option for uploading image

)
{
    constructor(): this("","","","")

}
