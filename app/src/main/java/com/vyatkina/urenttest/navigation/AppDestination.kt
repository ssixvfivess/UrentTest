package com.vyatkina.urenttest.navigation

sealed class AppDestination(val route: String) {

    data object Cities : AppDestination("cities")
    data object Maps : AppDestination("maps")

    data object CityDetails : AppDestination("city_details/{cityId}") {

        const val CITY_ID_ARG = "cityId"

        fun createRoute(cityId: Int): String {
            return "city_details/$cityId"
        }
    }
}
