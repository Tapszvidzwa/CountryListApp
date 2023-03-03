package com.example.countryapp.data.repository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CountryDao {
    @Query("SELECT * FROM countryInfo")
    fun getAllCountries(): List<CountryInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCountries(countries: List<CountryInfo>)

    @Query("SELECT COUNT(*) FROM countryInfo")
    fun getCount(): Int
}
