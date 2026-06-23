package com.example.companytime.presentation.navigation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey

@Composable
fun CompanyNavigationBar(
    selectedKey: Screen.Main,
    onSelectedKey: (Screen.Main) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier.height(64.dp).padding(8.dp)
            .shadow(elevation = 4.dp,
                shape = RoundedCornerShape(30.dp))
            .clip(RoundedCornerShape(30.dp))
            .border(width = 1.dp,
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(30.dp)
                ),
        containerColor = MaterialTheme.colorScheme.surface,
    ) {

        TOP_LEVEL_DESTINATIONS.forEach { (navKey, data) ->
            NavigationBarItem(
                selected = navKey == selectedKey,
                onClick = {
                    onSelectedKey(navKey)
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(data.icon),
                        contentDescription = "",
                        modifier = Modifier.size(32.dp)
                    )
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = MaterialTheme.colorScheme.surfaceBright,
                    unselectedIconColor = MaterialTheme.colorScheme.surfaceDim,
                    selectedIndicatorColor = MaterialTheme.colorScheme.secondary,
                    unselectedTextColor = Color.Transparent,
                    disabledTextColor = Color.Transparent,
                    selectedTextColor = Color.Transparent,
                    disabledIconColor = MaterialTheme.colorScheme.surfaceDim
                )
            )
        }
    }
}