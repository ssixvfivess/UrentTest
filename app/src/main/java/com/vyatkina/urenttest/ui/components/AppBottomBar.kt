package com.vyatkina.urenttest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vyatkina.urenttest.R

@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    isListSelected: Boolean,
    onListTabClick: () -> Unit,
    onMapTabClick: () -> Unit,
) {
    val screenBackground = colorResource(id = R.color.screen_background)
    val selectedTabBackground = colorResource(id = R.color.selected_tab_background)

    Surface(
        modifier = modifier,
        color = screenBackground,
        shadowElevation = 15.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 62.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(32.dp))
                    .background(if (isListSelected) selectedTabBackground else androidx.compose.ui.graphics.Color.Transparent)
                    .clickable(onClick = onListTabClick)
                    .padding(horizontal = 20.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center,
            ) {
                BottomTabIcon(isSelected = isListSelected)
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (!isListSelected) selectedTabBackground else androidx.compose.ui.graphics.Color.Transparent)
                    .clickable(onClick = onMapTabClick)
                    .padding(horizontal = 20.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center,
            ) {
                MapTabIcon(isSelected = !isListSelected)
            }
        }
    }
}

@Composable
private fun BottomTabIcon(isSelected: Boolean) {
    val selectedColor = colorResource(id = R.color.selected_tab_icon_color)
    val inactiveIconColor = colorResource(id = R.color.inactive_icon_color)

    Icon(
        painter = painterResource(id = R.drawable.list_button),
        contentDescription = null,
        tint = if (isSelected) selectedColor else inactiveIconColor,
        modifier = Modifier.size(24.dp),
    )
}

@Composable
private fun MapTabIcon(isSelected: Boolean) {
    val selectedColor = colorResource(id = R.color.selected_tab_icon_color)
    val inactiveIconColor = colorResource(id = R.color.inactive_icon_color)

    Icon(
        painter = painterResource(id = R.drawable.map_button),
        contentDescription = null,
        tint = if (isSelected) selectedColor else inactiveIconColor,
        modifier = Modifier.size(24.dp),
    )
}
