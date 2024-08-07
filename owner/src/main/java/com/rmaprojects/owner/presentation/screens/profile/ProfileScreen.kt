package com.rmaprojects.owner.presentation.screens.profile

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.rmaprojects.core.presentation.screen.SharedProfileScreen
import com.rmaprojects.owner.navigation.OwnerNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Destination<OwnerNavigation>
@Composable
fun OwnerProfileScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Profile") })
        }
    ) { innerPadding ->
        SharedProfileScreen(
            modifier = Modifier.padding(innerPadding),
            onProfileEdit = {},
            onLogOut = {}
        )
    }
}