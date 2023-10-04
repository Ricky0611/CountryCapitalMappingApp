package com.example.countrycomposeapp.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun CapitalScreen(capital: String) {
    Text(
        text = capital,
        textAlign = TextAlign.Center,
        color = Color.Blue
    )
}