package com.rmaprojects.owner.presentation.screens.details.branch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.rmaprojects.owner.navigation.OwnerNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Destination<OwnerNavigation>
@Composable
fun BranchDetailScreen(branchId: String?) {

    val isEditMode by remember {
        mutableStateOf(branchId != null)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {

        }
    }

}