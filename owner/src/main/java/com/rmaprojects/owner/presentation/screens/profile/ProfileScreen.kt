package com.rmaprojects.owner.presentation.screens.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.rmaprojects.core.common.screen.SharedProfileScreen
import com.rmaprojects.owner.navigation.OwnerNavigation

@Destination<OwnerNavigation>
@Composable
fun OwnerProfileScreen() {
    SharedProfileScreen()
}