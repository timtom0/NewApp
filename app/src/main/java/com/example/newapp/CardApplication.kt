package com.example.newapp

import android.app.Application
import com.example.newapp.data.AppContainer
import com.example.newapp.data.AppDataContainer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CardApplication: Application(){
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}