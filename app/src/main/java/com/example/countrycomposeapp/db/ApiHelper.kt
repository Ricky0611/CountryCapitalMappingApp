package com.example.countrycomposeapp.db

import com.example.countrycomposeapp.network.ApiService
import javax.inject.Inject

class ApiHelper @Inject constructor(private val apiService: ApiService) {

    suspend fun getCountries() = apiService.getCountries()
}