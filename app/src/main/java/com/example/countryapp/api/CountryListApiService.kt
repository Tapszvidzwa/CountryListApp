package com.example.countryapp.api

import com.example.countryapp.data.models.CountryList
import retrofit2.Response
import retrofit2.http.GET

interface CountryListApiService {

    @GET("peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json")
    suspend fun getCountryList(
    ): Response<CountryList>
}