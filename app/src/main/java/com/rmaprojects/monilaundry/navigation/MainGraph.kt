package com.rmaprojects.monilaundry.navigation

import com.ramcosta.composedestinations.annotation.ExternalNavGraph
import com.ramcosta.composedestinations.annotation.NavHostGraph
import com.ramcosta.composedestinations.generated.auth.navgraphs.AuthNavigationNavGraph
import com.ramcosta.composedestinations.generated.owner.navgraphs.OwnerNavigationNavGraph

@NavHostGraph
annotation class MainGraph {
    @ExternalNavGraph<OwnerNavigationNavGraph>
    @ExternalNavGraph<AuthNavigationNavGraph>(start = true)

    companion object Includes
}
