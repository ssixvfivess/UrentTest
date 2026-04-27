package com.vyatkina.urenttest.presentation.citydetails

data class CityDetailsState(
    val cityId: Int = 0,
    val isLoading: Boolean = true,
    val cityName: String = "",
    val countryName: String = "",
    val population: Int = 0,
    val errorMessage: String? = null,
)
