package com.vyatkina.urenttest.presentation.cities

import androidx.lifecycle.ViewModel
import com.vyatkina.urenttest.R
import com.vyatkina.urenttest.base.ResourceProvider
import com.vyatkina.urenttest.base.suspendRunCatching
import com.vyatkina.urenttest.domain.usecase.GetCityUseCase
import com.vyatkina.urenttest.presentation.cities.mapper.CityListItemUiMapper
import com.vyatkina.urenttest.presentation.cities.model.CityListItemUi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val getCityUseCase: GetCityUseCase,
    private val cityListItemUiMapper: CityListItemUiMapper,
    private val resourceProvider: ResourceProvider,
) : ViewModel(), ContainerHost<CitiesState, CitiesSideEffect> {

    override val container = container<CitiesState, CitiesSideEffect>(CitiesState())

    private val pageSize = 20

    init {
        loadCities(reset = true)
    }
    fun onSearchQueryChange(query: String) = intent {
        reduce {
            state.copy(
                searchQuery = query,
                errorMessage = null,
            )
        }
    }

    fun onSearchClick() {
        loadCities(reset = true)
    }

    fun onCityClick(city: CityListItemUi) = intent {
        postSideEffect(
            CitiesSideEffect.OpenCityDetails(city.id),
        )
    }

    fun onLoadNextPage() = intent {
        if (state.isLoading || state.isLoadingNextPage || !state.canLoadNextPage) return@intent
        loadCities(reset = false)
    }

    private fun loadCities(reset: Boolean) = intent {
        val nextPage = if (reset) 1 else state.currentPage + 1

        reduce {
            state.copy(
                isLoading = reset,
                isLoadingNextPage = !reset,
                errorMessage = null,
                canLoadNextPage = if (reset) true else state.canLoadNextPage,
            )
        }

        suspendRunCatching {
            getCityUseCase(
                query = state.searchQuery.trim().ifBlank { null },
                page = nextPage,
                limit = pageSize,
            )
        }.onSuccess { cities ->
            val mappedCities = cityListItemUiMapper.mapList(cities)
            reduce {
                state.copy(
                    cities = if (reset) {
                        mappedCities
                    } else {
                        state.cities + mappedCities
                    },
                    isLoading = false,
                    isLoadingNextPage = false,
                    currentPage = nextPage,
                    canLoadNextPage = mappedCities.size >= pageSize,
                    errorMessage = null,
                )
            }
        }.onFailure {
            val message = it.message ?: resourceProvider.getString(R.string.error_load_cities)
            reduce {
                state.copy(
                    isLoading = false,
                    isLoadingNextPage = false,
                    errorMessage = message,
                )
            }
            postSideEffect(CitiesSideEffect.ShowError(message))
        }
    }
}
