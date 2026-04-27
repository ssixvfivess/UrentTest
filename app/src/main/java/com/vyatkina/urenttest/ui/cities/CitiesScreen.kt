package com.vyatkina.urenttest.ui.cities

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vyatkina.urenttest.R
import com.vyatkina.urenttest.presentation.cities.model.CityListItemUi
import com.vyatkina.urenttest.ui.components.AppBottomBar
import com.vyatkina.urenttest.ui.theme.UrentTestTheme

@Composable
fun CitiesScreen(
    modifier: Modifier = Modifier,
    searchQuery: String = "",
    cities: List<CityListItemUi>,
    isLoading: Boolean,
    isLoadingNextPage: Boolean,
    canLoadNextPage: Boolean,
    errorMessage: String?,
    onSearchQueryChange: (String) -> Unit = {},
    onSearchClick: () -> Unit = {},
    onLoadNextPage: () -> Unit = {},
    onCityClick: (CityListItemUi) -> Unit = {},
    onListTabClick: () -> Unit = {},
    onMapTabClick: () -> Unit = {},
    searchIcon: @Composable () -> Unit = { SearchIconPlaceholder() },
    cityIcon: @Composable () -> Unit = { CityPinPlaceholder() },
) {
    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()
    val screenBackground = colorResource(id = R.color.screen_background)
    val titleColor = colorResource(id = R.color.title_color)
    val inactiveIconColor = colorResource(id = R.color.inactive_icon_color)

    val shouldLoadNextPage = remember(cities, listState, isLoading, isLoadingNextPage, canLoadNextPage) {
        val lastVisibleIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
        canLoadNextPage &&
            !isLoading &&
            !isLoadingNextPage &&
            cities.isNotEmpty() &&
            lastVisibleIndex >= cities.lastIndex - 2
    }

    LaunchedEffect(shouldLoadNextPage) {
        if (shouldLoadNextPage) {
            onLoadNextPage()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(screenBackground)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                focusManager.clearFocus()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.cities_title),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .padding(horizontal = 64.dp),
                color = titleColor,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
            )

            Spacer(modifier = Modifier.height(24.dp))

            SearchField(
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                onSearchClick = onSearchClick,
                searchIcon = searchIcon,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                when {
                    isLoading && cities.isEmpty() -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = titleColor,
                        )
                    }

                    errorMessage != null && cities.isEmpty() -> {
                        Text(
                            text = errorMessage,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(horizontal = 32.dp),
                            color = inactiveIconColor,
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                        )
                    }

                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            state = listState,
                        ) {
                            itemsIndexed(
                                items = cities,
                                key = { _, item -> item.id },
                            ) { index, city ->
                                CityRow(
                                    item = city,
                                    showDivider = index != cities.lastIndex,
                                    onClick = { onCityClick(city) },
                                    cityIcon = cityIcon,
                                )
                            }

                            if (isLoadingNextPage) {
                                item(key = "pagination_loader") {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 16.dp),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        CircularProgressIndicator(
                                            color = titleColor,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        AppBottomBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            isListSelected = true,
            onListTabClick = onListTabClick,
            onMapTabClick = onMapTabClick,
        )
    }
}

@Composable
private fun SearchField(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    searchIcon: @Composable () -> Unit,
) {
    var hasFocus by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val showFocusedBorder = hasFocus
    val searchFocusedBorder = colorResource(id = R.color.search_focused_border)
    val searchBackground = colorResource(id = R.color.search_background)
    val placeholderColor = colorResource(id = R.color.placeholder_color)
    val titleColor = colorResource(id = R.color.title_color)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = if (showFocusedBorder) 1.5.dp else 0.dp,
                color = if (showFocusedBorder) searchFocusedBorder else Color.Transparent,
                shape = RoundedCornerShape(16.dp),
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                focusRequester.requestFocus()
            },
        color = searchBackground,
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart,
            ) {
                if (searchQuery.isBlank()) {
                    Text(
                        text = stringResource(id = R.string.search_city_hint),
                        color = placeholderColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                    )
                }

                BasicTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .onFocusChanged { hasFocus = it.isFocused },
                    singleLine = true,
                    textStyle = remember {
                        androidx.compose.ui.text.TextStyle(
                            color = titleColor,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    },
                    cursorBrush = SolidColor(titleColor),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onSearchClick()
                            focusManager.clearFocus()
                        },
                    ),
                )
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        onSearchClick()
                        focusManager.clearFocus()
                    }
                    .padding(4.dp),
                contentAlignment = Alignment.Center,
            ) {
                searchIcon()
            }
        }
    }
}

@Composable
private fun CityRow(
    item: CityListItemUi,
    showDivider: Boolean,
    onClick: () -> Unit,
    cityIcon: @Composable () -> Unit,
) {
    val titleColor = colorResource(id = R.color.title_color)
    val dividerColor = colorResource(id = R.color.divider_color)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 13.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp),
                contentAlignment = Alignment.Center,
            ) {
                cityIcon()
            }

            Spacer(modifier = Modifier.size(12.dp))

            Text(
                text = item.title,
                color = titleColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }

        if (showDivider) {
            HorizontalDivider(color = dividerColor, thickness = 1.dp)
        }
    }
}

@Composable
private fun SearchIconPlaceholder() {
    Icon(
        painter = painterResource(id = R.drawable.search_button),
        contentDescription = null,
        tint = Color.Unspecified,
        modifier = Modifier.size(24.dp),
    )
}

@Composable
private fun CityPinPlaceholder() {
    Icon(
        painter = painterResource(id = R.drawable.place_pin),
        contentDescription = null,
        tint = Color.Unspecified,
        modifier = Modifier.size(24.dp),
    )
}

private val previewCities = listOf(
    CityListItemUi(id = 1, title = "Moscow, RU", cityName = "Moscow", countryName = "RU", population = 12_655_000),
    CityListItemUi(id = 2, title = "London, UK", cityName = "London", countryName = "UK", population = 8_799_800),
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CitiesScreenPreview() {
    UrentTestTheme {
        CitiesScreen(
            cities = previewCities,
            isLoading = false,
            isLoadingNextPage = false,
            canLoadNextPage = true,
            errorMessage = null,
        )
    }
}
