package com.vyatkina.urenttest.ui.citydetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vyatkina.urenttest.R
import com.vyatkina.urenttest.presentation.citydetails.CityDetailsState
import com.vyatkina.urenttest.ui.theme.UrentTestTheme
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

@Composable
fun CityDetailsScreen(
    state: CityDetailsState,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onSearchInfoClick: () -> Unit = {},
) {
    val screenBackground = colorResource(id = R.color.screen_background)
    val titleColor = colorResource(id = R.color.title_color)
    val buttonGradientStart = colorResource(id = R.color.button_gradient_start)
    val buttonGradientEnd = colorResource(id = R.color.button_gradient_end)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(screenBackground)
            .navigationBarsPadding()
            .padding(top = 20.dp, start = 16.dp, end = 16.dp, bottom = 20.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .clickable(onClick = onBackClick),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_left_button),
                    contentDescription = stringResource(id = R.string.back_button),
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(22.dp),
                )
            }

            Text(
                text = stringResource(id = R.string.city_details_title),
                modifier = Modifier
                    .padding(start = 24.dp),
                color = titleColor,
                textAlign = TextAlign.Left,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
            )

            Spacer(modifier = Modifier.size(26.dp))
        }

        Spacer(modifier = Modifier.size(32.dp))

        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        color = titleColor,
                    )
                }
            }

            state.errorMessage != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = state.errorMessage,
                        color = titleColor,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                    )
                }
            }

            else -> {
                DetailSection(
                    title = stringResource(id = R.string.city_label),
                    value = state.cityName,
                )

                Spacer(modifier = Modifier.size(20.dp))

                DetailSection(
                    title = stringResource(id = R.string.country_label),
                    value = state.countryName,
                )

                Spacer(modifier = Modifier.size(20.dp))

                DetailSection(
                    title = stringResource(id = R.string.population_label),
                    value = stringResource(
                        id = R.string.population_value,
                        formatPopulation(state.population),
                    ),
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        }

        Button(
            onClick = onSearchInfoClick,
            modifier = Modifier
                .fillMaxWidth()
                .size(height = 56.dp, width = 0.dp),
            enabled = !state.isLoading && state.errorMessage == null && state.cityName.isNotBlank(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = androidx.compose.ui.graphics.Color.Transparent,
            ),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(buttonGradientStart, buttonGradientEnd),
                        ),
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(id = R.string.search_city_info_button),
                    color = colorResource(id = R.color.white),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}

@Composable
private fun DetailSection(
    title: String,
    value: String,
) {
    val titleColor = colorResource(id = R.color.title_color)

    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Text(
            text = title,
            color = titleColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = value,
            color = titleColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
        )
    }
}

private fun formatPopulation(population: Int): String {
    val symbols = DecimalFormatSymbols().apply {
        groupingSeparator = ' '
    }
    return DecimalFormat("#,###", symbols).format(population)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CityDetailsScreenPreview() {
    UrentTestTheme {
        CityDetailsScreen(
            state = CityDetailsState(
                isLoading = false,
                cityName = "Москва",
                countryName = "Россия",
                population = 12_655_000,
            ),
        )
    }
}
