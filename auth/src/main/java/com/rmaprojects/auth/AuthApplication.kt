package com.rmaprojects.auth

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.navgraphs.AuthNavigationNavGraph
import com.ramcosta.composedestinations.utils.currentDestinationAsState

@Destination<RootGraph>(start = true)
@Composable
fun AuthApplication() {
    DestinationsNavHost(navGraph = AuthNavigationNavGraph)

}