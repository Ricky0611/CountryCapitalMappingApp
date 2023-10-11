package com.example.countrycomposeapp

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.countrycomposeapp.data.Country
import com.example.countrycomposeapp.db.ApiHelper
import com.example.countrycomposeapp.db.MainRepository
import com.example.countrycomposeapp.network.ApiClient
import com.example.countrycomposeapp.network.ApiService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.BufferedReader
import java.io.InputStream

@SmallTest
@RunWith(AndroidJUnit4::class)
class MainRepositoryTest {
    private lateinit var apiService: ApiService
    private lateinit var apiHelper: ApiHelper
    private lateinit var mainRepository: MainRepository
    private lateinit var mContext: Context

    @Before
    fun setup() {
        apiService = ApiClient.getApiService()
        apiHelper = ApiHelper(apiService)
        mainRepository = MainRepository((apiHelper))
        mContext = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun testGetCountriesWithContext() {
        val inputStream: InputStream = mContext.assets.open("countries.json")
        Assert.assertNotNull(inputStream)

        val bufferedReader: BufferedReader = inputStream.bufferedReader()
        Assert.assertNotNull(bufferedReader)

        val content: String = bufferedReader.readText()
        Assert.assertNotNull(content)

        val listCountryType = object : TypeToken<List<Country>>() {}.type
        val response: List<Country> = Gson().fromJson(content, listCountryType)
        Assert.assertNotNull(response)

        Assert.assertNotNull(mainRepository.getCountries(mContext))
        Assert.assertEquals(response, mainRepository.getCountries(mContext))
    }
}