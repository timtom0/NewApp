package com.example.newapp

import android.app.Application
import com.example.newapp.data.CardDatabase
import com.example.newapp.data.CardRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CardApplication: Application(){
    private val database by lazy { CardDatabase.getDatabase(this) }
    val repository by lazy { CardRepository(database.cardDao()) }
}