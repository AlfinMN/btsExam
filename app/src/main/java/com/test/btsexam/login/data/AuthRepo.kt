package com.test.btsexam.login.data

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.test.btsexam.utils.ResponseAPI
import com.test.btsexam.utils.Token
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class AuthRepo @Inject constructor(val authAPI: AuthAPI) {
    var resApi = MutableLiveData<ResponseAPI>()
    var resAuth = MutableLiveData<ResponseAuth>()
    private lateinit var tokenOuth: Token

    fun register(registerModel: RegisterModel,context: Context){

        authAPI.register(registerModel).enqueue(object : Callback<ResponseAPI>{
            override fun onResponse(call: Call<ResponseAPI>, response: Response<ResponseAPI>) {
                val res = response.body()
                if (res?.statusCode == 2000){
                    resApi.value = res

                } else {
                    Toast.makeText(context, "Register Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseAPI>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context,"Connection Failed ", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun login(authModel: AuthModel,context: Context){
        tokenOuth = Token(context)
        authAPI.login(authModel).enqueue(object : Callback<ResponseAuth>{
            override fun onResponse(call: Call<ResponseAuth>, response: Response<ResponseAuth>) {
              val res = response.body()
                if (res!=null){
                    if (res.statusCode == 2110){
                        resAuth.value = res
                        tokenOuth.saveAuthToken(res.data.token)
                        println("${res.data.token}")
                        Toast.makeText(context, "Login Sukses", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, res.errorMessage, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Username dan password salah", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseAuth>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context,"Connection Failed ", Toast.LENGTH_SHORT).show()
            }

        })
    }
}