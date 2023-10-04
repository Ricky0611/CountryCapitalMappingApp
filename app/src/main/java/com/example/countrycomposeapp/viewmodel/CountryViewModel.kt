package com.example.countrycomposeapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countrycomposeapp.data.Country
import com.example.countrycomposeapp.db.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(private val mainRepository: MainRepository): ViewModel() {

    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>> = _countries

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = mainRepository.getCountries()
                if (response.isSuccessful) {
                    if (response.body().isNullOrEmpty()) {
                        _countries.postValue(emptyList())
                    } else {
                        _countries.postValue(response.body())
                    }
                } else {
                    response.errorBody()?.let {
                        _error.postValue(it.string())
                    } ?: kotlin.run {
                        _error.postValue("Unknown Error")
                    }
                }
            } catch (e:Exception) {
                e.printStackTrace()
                _error.postValue(e.message)
            }
        }
    }

    fun getCountries(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = mainRepository.getCountries(context)
                if (response.isEmpty()) {
                    _countries.postValue(emptyList())
                } else {
                    _countries.postValue(response)
                }
            } catch (e:Exception) {
                e.printStackTrace()
                _error.postValue(e.message)
            }
        }
    }
}