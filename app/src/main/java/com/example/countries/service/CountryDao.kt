package com.example.countries.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.countries.model.Country

@Dao
interface CountryDao {

    //Data Access Object

    @Insert
    suspend fun insertAll(vararg countries: Country) : List<Long>

    //Insert -> INSERT INTO
    //suspend -> coroutine içerisinde çağırılıyor, fonksiyonları durdurup devam etmeye yarıyor.
    //vararg -> multiple country object ,istediğimiz zaman istediğimiz kadar vermemiz lazım
    //List<Long> -> primary keys döndürüyor

    @Query("SELECT * FROM country")
    suspend fun getAllCountries() : List<Country>

    @Query("SELECT * FROM country WHERE uuid = :countryId")
    suspend fun getCountry(countryId: Int) : Country

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()

}