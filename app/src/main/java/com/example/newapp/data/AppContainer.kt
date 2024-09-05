package com.example.newapp.data

import android.content.Context

interface AppContainer {
    val cardsRepository: CardsRepository
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val cardsRepository: CardsRepository by lazy {
        CardRepository(CardDatabase.getDatabase(context).cardDao())
    }
}