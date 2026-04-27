package com.vyatkina.urenttest.presentation.cities

import com.vyatkina.urenttest.presentation.cities.model.CityListItemUi

data class CitiesState(
    val searchQuery: String = "",
    val cities: List<CityListItemUi> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingNextPage: Boolean = false,
    val currentPage: Int = 0,
    val canLoadNextPage: Boolean = true,
    val errorMessage: String? = null,
)
