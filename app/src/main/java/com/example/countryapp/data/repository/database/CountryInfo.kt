package com.example.countryapp.data.repository.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CountryInfo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "region") val region: String?,
    @ColumnInfo(name = "code") val code: String?,
    @ColumnInfo(name = "capital") val capital: String?,
)