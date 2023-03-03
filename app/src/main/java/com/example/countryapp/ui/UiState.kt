package com.example.countryapp.ui

import android.view.View
import com.example.countryapp.data.repository.database.CountryInfo

data class UiState(
    val countriesList: List<CountryInfo> = emptyList(),
    val errorMessage: String = "",
    val showLoadingSpinner: Int = View.INVISIBLE,
    val state: ScreenState = ScreenState.IDLE
)