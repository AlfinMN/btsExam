package com.test.btsexam.config


import com.test.btsexam.login.ui.AuthActivity
import com.test.btsexam.login.ui.LoginFragment
import com.test.btsexam.login.ui.RegisterFragment
import dagger.Component

@Component(modules = [NetworkModul::class])
interface ApplicationComponent {
    fun inject(authActivity: AuthActivity)
    fun inject(loginFragment: LoginFragment)
    fun inject(registerFragment: RegisterFragment)
}