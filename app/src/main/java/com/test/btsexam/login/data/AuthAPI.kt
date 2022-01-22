package com.test.btsexam.login.data

import com.test.btsexam.utils.ResponseAPI
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {
    @POST("register")
    fun register(@Body registerModel : RegisterModel):Call<ResponseAPI>

    @POST("login")
    fun login(@Body authModel: AuthModel):Call<ResponseAuth>

}