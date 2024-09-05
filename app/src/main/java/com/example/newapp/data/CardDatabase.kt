package com.example.newapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newapp.model.CardItem

@Database(entities = [CardItem::class], version = 1, exportSchema = false)
abstract class CardDatabase : RoomDatabase() {

    abstract fun cardDao(): CardDao

    companion object {
        @Volatile
        private var Instance: CardDatabase? = null

        fun getDatabase(context: Context): CardDatabase {
                       return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, CardDatabase::class.java, "item_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
