package com.example.bmi.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bmi.Room.RecordDao

@Database(entities = arrayOf(BmiRecord::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun RecordDao(): RecordDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "record_history"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}