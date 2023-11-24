package com.example.countries.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.countries.model.Country

@Database(entities = arrayOf(Country::class), version = 1)
abstract class CountryDatabase : RoomDatabase() {
    abstract fun countryDao() : CountryDao

    //Singleton içerisinde tek bir obje oluşturulam

    companion object{
        @Volatile private var instance : CountryDatabase? = null

        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,CountryDatabase::class.java,"countrydatabase"
        ).build()
    }
}