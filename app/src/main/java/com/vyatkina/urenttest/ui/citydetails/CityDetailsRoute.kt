package com.vyatkina.urenttest.ui.citydetails

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.vyatkina.urenttest.presentation.citydetails.CityDetailsSideEffect
import com.vyatkina.urenttest.presentation.citydetails.CityDetailsViewModel
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun CityDetailsRoute(
    onBackClick: () -> Unit,
    viewModel: CityDetailsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is CityDetailsSideEffect.OpenBrowser -> {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(sideEffect.uri),
                )
                context.startActivity(intent)
            }
        }
    }

    CityDetailsScreen(
        state = state,
        onBackClick = onBackClick,
        onSearchInfoClick = viewModel::onSearchInfoClick,
    )
}
