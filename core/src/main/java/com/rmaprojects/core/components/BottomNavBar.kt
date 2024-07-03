package com.rmaprojects.core.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.rmaprojects.core.common.BottomBarNavigation

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    bottomNavBarList: List<BottomBarNavigation>,
    currentDestination: DestinationSpec,
    onClick: (DestinationSpec) -> Unit,
) {

    NavigationBar(
        modifier = modifier
    ) {
        bottomNavBarList.forEach { destination ->
            NavigationBarItem(
                icon = { Icon(destination.icon, destination.label) },
                label = { Text(destination.label) },
                onClick = {onClick(destination.direction)},
                selected = currentDestination == destination.direction
            )
        }
    }

}
