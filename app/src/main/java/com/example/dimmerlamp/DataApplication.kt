package com.example.dimmerlamp

import android.app.Application
import com.example.dimmerlamp.data.AppContainer
import com.example.dimmerlamp.data.DefaultAppContainer

class DataApplication : Application(){
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}