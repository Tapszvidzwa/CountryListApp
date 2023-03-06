package com.example.countryapp.ui.viewmodels

import android.app.Application
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.countryapp.api.Result
import com.example.countryapp.api.RetrofitHelper
import com.example.countryapp.data.repository.CountryListRepository
import com.example.countryapp.data.repository.database.CountryDatabase
import com.example.countryapp.ui.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CountryListViewModel(private val repository: CountryListRepository) : ViewModel() {

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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val countryDb =
                    CountryDatabase.getInstance(this[APPLICATION_KEY] as Application).countryDao()
                val apiService = RetrofitHelper.createApiService()
                val repository = CountryListRepository(countryDb, apiService)
                CountryListViewModel(
                    repository
                )
            }
        }
    }

}

enum class ScreenState {
    IDLE,
    LOADING,
    SUCCESS,
    ERROR
}