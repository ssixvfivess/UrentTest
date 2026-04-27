package com.vyatkina.urenttest.presentation.citydetails

sealed interface CityDetailsSideEffect {
    data class OpenBrowser(val uri: String) : CityDetailsSideEffect
}
