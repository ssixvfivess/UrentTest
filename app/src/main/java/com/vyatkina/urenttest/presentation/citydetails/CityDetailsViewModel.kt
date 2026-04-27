package com.vyatkina.urenttest.presentation.citydetails

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.vyatkina.urenttest.R
import com.vyatkina.urenttest.base.ResourceProvider
import com.vyatkina.urenttest.base.suspendRunCatching
import com.vyatkina.urenttest.domain.usecase.GetCityByIdUseCase
import com.vyatkina.urenttest.navigation.AppDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

@HiltViewModel
class CityDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCityByIdUseCase: GetCityByIdUseCase,
    private val resourceProvider: ResourceProvider,
) : ViewModel(), ContainerHost<CityDetailsState, CityDetailsSideEffect> {

    private val cityId = savedStateHandle.get<Int>(AppDestination.CityDetails.CITY_ID_ARG) ?: 0

    override val container = container<CityDetailsState, CityDetailsSideEffect>(
        CityDetailsState(
            cityId = cityId,
        ),
    )

    init {
        loadCity()
    }

    fun onSearchInfoClick() = intent {
        if (state.cityName.isBlank()) return@intent
        postSideEffect(
            CityDetailsSideEffect.OpenBrowser(
                uri = "https://www.google.com/search?q=${Uri.encode(state.cityName)}",
            ),
        )
    }

    private fun loadCity() = intent {
        reduce { state.copy(isLoading = true, errorMessage = null) }

        val city = suspendRunCatching { getCityByIdUseCase(cityId) }.getOrNull()

        if (city == null) {
            reduce {
                state.copy(
                    isLoading = false,
                    errorMessage = resourceProvider.getString(R.string.error_load_city_details),
                )
            }
            return@intent
        }

        reduce {
            state.copy(
                isLoading = false,
                cityName = city.name,
                countryName = city.country,
                population = city.population,
                errorMessage = null,
            )
        }
    }
}
