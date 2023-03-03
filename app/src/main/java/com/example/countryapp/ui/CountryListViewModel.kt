package com.example.countryapp.ui

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.countryapp.api.Result
import com.example.countryapp.data.repository.CountryListRepository
import com.example.countryapp.data.repository.database.CountryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CountryListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CountryListRepository

    init {
        val countryDb = CountryDatabase.getInstance(application).countryDao()
        repository = CountryListRepository(countryDb)
    }

    private val _uiState = MutableStateFlow(UiState())

    val uiState: StateFlow<UiState>
        get() = _uiState

    fun fetchCountryList() {
        _uiState.value =
            _uiState.value.copy(showLoadingSpinner = View.VISIBLE, state = ScreenState.LOADING)

        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getCountriesInfo()

            withContext(Dispatchers.Main) {
                when (result) {
                    is Result.Success -> {
                        result.data?.let {
                            _uiState.value = UiState(
                                countriesList = result.data,
                                showLoadingSpinner = View.INVISIBLE,
                                state = ScreenState.SUCCESS
                            )
                        }
                    }

                    is Result.Error -> {
                        result.message?.let {
                            _uiState.value = UiState(
                                countriesList = emptyList(),
                                showLoadingSpinner = View.INVISIBLE,
                                errorMessage = it,
                                state = ScreenState.ERROR
                            )
                        }
                    }
                }
            }
        }
    }

    fun retry() {
        fetchCountryList()
    }
}

enum class ScreenState {
    IDLE,
    LOADING,
    SUCCESS,
    ERROR
}