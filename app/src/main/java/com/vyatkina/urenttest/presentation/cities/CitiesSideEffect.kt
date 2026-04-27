package com.vyatkina.urenttest.presentation.cities

sealed interface CitiesSideEffect {
    data class ShowError(val message: String) : CitiesSideEffect
    data class OpenCityDetails(val cityId: Int) :
        CitiesSideEffect
}
