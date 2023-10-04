package com.example.countrycomposeapp.db

import android.content.Context
import com.example.countrycomposeapp.data.Country
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject


class MainRepository @Inject constructor(private val apiHelper: ApiHelper){

    suspend fun getCountries() = apiHelper.getCountries()

    fun getCountries(context: Context): List<Country> {
        lateinit var jsonString: String
        try {
            jsonString = context.assets.open("countries.json").bufferedReader().use {
                it.readText()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val listCountryType = object : TypeToken<List<Country>>() {}.type
        return Gson().fromJson(jsonString, listCountryType)
    }
}