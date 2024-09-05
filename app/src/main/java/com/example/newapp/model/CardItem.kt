package com.example.newapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cardsData")
data class CardItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val info: String
)
