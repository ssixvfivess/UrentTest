package com.vyatkina.urenttest.ui.cities

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.vyatkina.urenttest.presentation.cities.CitiesSideEffect
import com.vyatkina.urenttest.presentation.cities.CitiesViewModel
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun CitiesRoute(
    onOpenCityDetails: (Int) -> Unit,
    onOpenMaps: () -> Unit,
    viewModel: CitiesViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is CitiesSideEffect.OpenCityDetails -> onOpenCityDetails(sideEffect.cityId)
            is CitiesSideEffect.ShowError -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { _ ->
        CitiesScreen(
            searchQuery = state.searchQuery,
            cities = state.cities,
            isLoading = state.isLoading,
            isLoadingNextPage = state.isLoadingNextPage,
            canLoadNextPage = state.canLoadNextPage,
            errorMessage = state.errorMessage,
            onSearchQueryChange = viewModel::onSearchQueryChange,
            onSearchClick = viewModel::onSearchClick,
            onLoadNextPage = viewModel::onLoadNextPage,
            onCityClick = viewModel::onCityClick,
            onMapTabClick = onOpenMaps,
        )
    }
}
