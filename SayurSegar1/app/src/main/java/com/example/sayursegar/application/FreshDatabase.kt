package com.example.sayursegar.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sayursegar.dao.FreshDao
import com.example.sayursegar.model.Fresh

@Database(entities = [Fresh::class], version = 1, exportSchema = false)
abstract class FreshDatabase: RoomDatabase(){
    abstract fun freshDao(): FreshDao

    companion object{
        private var INSTANCE: FreshDatabase? = null

        fun getDatabase(context: Context): FreshDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FreshDatabase::class.java,
                    "fresh_database"
                )
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
