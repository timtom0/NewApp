package com.example.newapp.data

import com.example.newapp.model.CardItem
import kotlinx.coroutines.flow.Flow

interface CardsRepository {

    fun getAll(): Flow<List<CardItem>>
    fun getCard(id: Int): Flow<CardItem?>

    suspend fun insert(card: CardItem)
    suspend fun delete(card: CardItem)
    suspend fun update(card: CardItem)

}

class CardRepository(private val cardDao: CardDao) : CardsRepository {
    override fun getAll(): Flow<List<CardItem>> = cardDao.getAll()

    override fun getCard(id: Int): Flow<CardItem?> = cardDao.getCard(id)

    override suspend fun insert(card: CardItem) = cardDao.insert(card)

    override suspend fun delete(card: CardItem) = cardDao.delete(card)

    override suspend fun update(card: CardItem) = cardDao.update(card)
}