package com.example.countryapp.api

import android.net.Uri
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitHelper {
    fun createApiService(): CountryListApiService {

        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(HttpClient.client)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .registerTypeAdapter(Uri::class.java, UriTypeAdapter())
                        .create()
                )
            )
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(CountryListApiService::class.java)
    }
}
