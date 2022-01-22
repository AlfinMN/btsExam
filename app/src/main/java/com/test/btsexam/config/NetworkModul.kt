package com.test.btsexam.config


import com.test.btsexam.login.data.AuthAPI
import dagger.Module
import dagger.Provides


@Module
class NetworkModul {
    @Provides
    fun provideLoginAPI(): AuthAPI {
        return Connection.urlCon().create(AuthAPI::class.java)
    }


}