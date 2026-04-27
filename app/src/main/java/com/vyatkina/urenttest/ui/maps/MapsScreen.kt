package com.vyatkina.urenttest.ui.maps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vyatkina.urenttest.R
import com.vyatkina.urenttest.ui.components.AppBottomBar

@Composable
fun MapsScreen(
    modifier: Modifier = Modifier,
    onListTabClick: () -> Unit = {},
    onMapTabClick: () -> Unit = {},
) {
    val screenBackground = colorResource(id = R.color.screen_background)
    val titleColor = colorResource(id = R.color.title_color)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(screenBackground),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp, start = 16.dp, end = 16.dp),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(id = R.string.maps_title),
                    color = titleColor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }

        AppBottomBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            isListSelected = false,
            onListTabClick = onListTabClick,
            onMapTabClick = onMapTabClick,
        )
    }
}
