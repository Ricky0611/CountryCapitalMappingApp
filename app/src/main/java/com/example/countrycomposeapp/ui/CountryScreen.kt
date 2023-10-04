package com.example.countrycomposeapp.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.countrycomposeapp.utils.ConnectionState
import com.example.countrycomposeapp.utils.connectivityState
import com.example.countrycomposeapp.viewmodel.CountryViewModel

@Composable
fun CountryScreen(onCountryClick: (String) -> Unit) {
    val context = LocalContext.current

    val countryViewModel: CountryViewModel = hiltViewModel()
    val connection by connectivityState()

    val countryList = countryViewModel.countries.observeAsState()
    val errorMessage = countryViewModel.error.observeAsState()

    countryList.value?.let {
        LazyColumn {
            items(it) { country ->
                TextButton(
                    onClick = {
                        onCountryClick(country.capital)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .border(1.dp, Color.Gray),
                ) {
                    Text(
                        text = country.name,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    errorMessage.value?.let {
        Text(
            text = it,
            color = Color.Red
        )
    }

    if (connection === ConnectionState.Available) {
        countryViewModel.getCountries()
    } else {
        countryViewModel.getCountries(context)
    }

}