package com.vyatkina.urenttest

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vyatkina.urenttest.navigation.AppDestination
import com.vyatkina.urenttest.ui.cities.CitiesRoute
import com.vyatkina.urenttest.ui.citydetails.CityDetailsRoute
import com.vyatkina.urenttest.ui.maps.MapsScreen
import com.vyatkina.urenttest.ui.theme.UrentTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UrentTestTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
private fun AppNavigation() {
    val navController = rememberNavController()
    val statusBarColor = colorResource(id = R.color.screen_background)

    ApplySystemBarsStyle(backgroundColor = statusBarColor)

    NavHost(
        navController = navController,
        startDestination = AppDestination.Cities.route,
    ) {
        composable(AppDestination.Cities.route) {
            CitiesRoute(
                onOpenCityDetails = { cityId ->
                    navController.navigate(AppDestination.CityDetails.createRoute(cityId))
                },
                onOpenMaps = {
                    navController.navigate(AppDestination.Maps.route)
                },
            )
        }

        composable(AppDestination.Maps.route) {
            MapsScreen(
                onListTabClick = {
                    navController.navigate(AppDestination.Cities.route) {
                        popUpTo(AppDestination.Cities.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onMapTabClick = {},
            )
        }

        composable(
            route = AppDestination.CityDetails.route,
            arguments = listOf(
                navArgument(AppDestination.CityDetails.CITY_ID_ARG) {
                    type = NavType.IntType
                },
            ),
        ) {
            CityDetailsRoute(
                onBackClick = { navController.popBackStack() },
            )
        }
    }
}

@Composable
private fun ApplySystemBarsStyle(
    backgroundColor: Color,
) {
    val context = LocalContext.current
    val activity = context as? Activity ?: return

    LaunchedEffect(activity, backgroundColor) {
        activity.window.statusBarColor = backgroundColor.toArgb()
        WindowCompat.getInsetsController(activity.window, activity.window.decorView).apply {
            isAppearanceLightStatusBars = backgroundColor.luminance() > 0.5f
        }
    }
}
