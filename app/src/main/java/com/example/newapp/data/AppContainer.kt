package com.example.newapp.data

import android.content.Context

interface AppContainer {
    val cardsRepository: CardRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val cardsRepository: CardRepository by lazy {
        CardRepository(CardDatabase.getDatabase(context).cardDao())
    }
}