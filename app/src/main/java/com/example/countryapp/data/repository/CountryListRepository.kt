package com.example.countryapp.data.repository

import com.example.countryapp.api.Result
import com.example.countryapp.api.RetrofitHelper
import com.example.countryapp.data.models.CountryListItem
import com.example.countryapp.data.repository.database.CountryDao
import com.example.countryapp.data.repository.database.CountryInfo
import retrofit2.HttpException

class CountryListRepository(private val countryDao: CountryDao) {
    // Since we aren't using dagger, I am creating api service here
    private val countryListApiService = RetrofitHelper.createApiService()

    suspend fun getCountriesInfo(): Result<List<CountryInfo>> {
        try {
            // Check if already have a locally cached list
            return if (countryDao.getCount() != 0) {

                // Lets return list from cache if available
                Result.Success(data = countryDao.getAllCountries())
            } else {

                // Otherwise, fetch list
                val response = countryListApiService.getCountryList()

                if (response.isSuccessful && response.body() != null) {
                    // Cache list
                    countryDao.insertAllCountries(response.body()!!.map { it.toCountryInfo() })
                    Result.Success(data = countryDao.getAllCountries())
                } else {
                    Result.Error(message = response.errorBody().toString())
                }
            }

        } catch (httpException: HttpException) {
            return Result.Error(message = "Network error occured")
        } catch (exception: Exception) {
            return Result.Error(message = "An error occured: " + exception.message.toString())
        }
    }
}

fun CountryListItem.toCountryInfo(): CountryInfo {
    return CountryInfo(
        name = this.name,
        region = this.region,
        code = this.code,
        capital = this.capital
    )
}

