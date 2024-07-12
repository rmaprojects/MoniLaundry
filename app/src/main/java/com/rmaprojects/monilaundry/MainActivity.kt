package com.rmaprojects.monilaundry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.auth.destinations.AuthScreenDestination
import com.ramcosta.composedestinations.generated.employee.navgraphs.EmployeeNavigationNavGraph
import com.ramcosta.composedestinations.generated.owner.destinations.EmployeeMenuScreenDestination
import com.ramcosta.composedestinations.generated.owner.destinations.OwnerDashboardScreenDestination
import com.ramcosta.composedestinations.generated.owner.navgraphs.OwnerNavigationNavGraph
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import com.ramcosta.composedestinations.utils.startDestination
import com.rmaprojects.auth.common.AuthState
import com.rmaprojects.monilaundry.navigation.ownerBottomBarList
import com.rmaprojects.monilaundry.ui.components.BottomBar
import com.rmaprojects.monilaundry.ui.theme.MoniLaundryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoniLaundryTheme {

                val navController = rememberNavController()

                val currentDestination = navController.currentDestinationAsState().value
                    ?: if (AuthState.loggedRole.value == "owner") OwnerNavigationNavGraph.startDestination
                    else EmployeeNavigationNavGraph.startDestination

                val bottomBarList = ownerBottomBarList

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {

                    },
                    bottomBar = {
                        if (bottomBarList.any { it.direction == currentDestination }) {
                            BottomBar(
                                bottomBarList = bottomBarList,
                                navController = navController
                            )
                        }
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        DestinationsNavHost(
                            NavGraphs.main,
                            startRoute = if (!AuthState.isLoggedIn.value) {
                                AuthScreenDestination
                            } else {
                                if (AuthState.loggedRole.value == "owner") {
                                    OwnerDashboardScreenDestination
                                } else {
                                    EmployeeMenuScreenDestination
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}