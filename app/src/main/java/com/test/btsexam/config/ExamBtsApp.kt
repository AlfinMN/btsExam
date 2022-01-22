package com.test.btsexam.config

import android.app.Application

class ExamBtsApp : Application() {
    val applicationComponent : ApplicationComponent = DaggerApplicationComponent.create()
}