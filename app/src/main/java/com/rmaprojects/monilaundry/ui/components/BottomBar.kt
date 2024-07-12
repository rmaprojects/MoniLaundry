package com.rmaprojects.monilaundry.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import com.ramcosta.composedestinations.utils.startDestination
import com.rmaprojects.monilaundry.navigation.BottomBarDestination

@Composable
fun BottomBar(
    bottomBarList: List<BottomBarDestination>,
    navController: NavController,
    modifier: Modifier = Modifier
) {

    val currentDestination = navController.currentDestinationAsState().value
        ?: NavGraphs.main.nestedNavGraphs[0].startDestination

    NavigationBar(
        modifier = modifier
    ) {
        bottomBarList.forEach { destination ->
            NavigationBarItem(
                selected = currentDestination == destination.direction,
                onClick = {
                    navController.navigate(destination.direction)
                },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = destination.label
                    )
                },
                label = {
                    Text(text = destination.label)
                }
            )
        }
    }

}