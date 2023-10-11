package com.example.countrycomposeapp

import com.example.countrycomposeapp.data.Country
import com.example.countrycomposeapp.db.ApiHelper
import com.example.countrycomposeapp.db.MainRepository
import com.example.countrycomposeapp.network.ApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

class MainRepositoryTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    private val apiService = mockk<ApiService>()
    private lateinit var apiHelper: ApiHelper
    private lateinit var mainRepository: MainRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        apiHelper = ApiHelper(apiService)
        mainRepository = MainRepository((apiHelper))
    }

    @Test
    fun `test getCountries with network`() {
        runTest(StandardTestDispatcher()) {
            val response = Response.success(emptyList<Country>())
            coEvery{
                apiHelper.getCountries()
            } returns response

            mainRepository.getCountries()

            coVerify {
                apiHelper.getCountries()
            }
            assertEquals(response, mainRepository.getCountries())
        }
    }

}