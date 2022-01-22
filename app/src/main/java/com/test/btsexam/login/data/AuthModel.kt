package com.test.btsexam.login.data

class AuthModel(
    val password : String ,
    val username : String
) {

}
class RegisterModel(
val email : String,
val password : String ,
val username : String
){}

class ResponseAuth(
    var statusCode : Int,
    var message : String,
    var errorMessage : String,
    var data : TokenRes){

}

class TokenRes(
    val token: String
){}

