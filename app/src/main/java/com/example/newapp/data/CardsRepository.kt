package com.example.newapp.data

import androidx.annotation.WorkerThread
import com.example.newapp.model.CardItem
import kotlinx.coroutines.flow.Flow

class CardRepository(private val cardDao: CardDao) {

    val allcards: Flow<List<CardItem>> = cardDao.getAll()

    @WorkerThread
    suspend fun insert(card: CardItem)
    {
        cardDao.insert(card)
    }

    @WorkerThread
    suspend fun update(card: CardItem)
    {
        cardDao.update(card)
    }

    @WorkerThread
    suspend fun delete(card: CardItem)
    {
        cardDao.delete(card)
    }
}