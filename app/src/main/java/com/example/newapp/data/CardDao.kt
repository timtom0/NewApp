package com.example.newapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.newapp.model.CardItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    @Insert
    suspend fun insert(card: CardItem)

    @Delete
    suspend fun delete(card: CardItem)

    @Update
    suspend fun update(card: CardItem)

    @Query("SELECT * from cardsData WHERE id = :id")
    fun getCard(id: Int): Flow<CardItem>

    @Query("SELECT * from cardsData ORDER BY id ASC")
    fun getAll(): Flow<List<CardItem>>
}