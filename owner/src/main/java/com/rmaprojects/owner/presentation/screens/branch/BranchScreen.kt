package com.rmaprojects.owner.presentation.screens.branch

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rmaprojects.core.presentation.components.BranchCard
import com.rmaprojects.core.presentation.screen.SharedErrorScreen
import com.rmaprojects.core.presentation.screen.SharedLoadingScreen
import com.rmaprojects.owner.navigation.OwnerNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Destination<OwnerNavigation>
@Composable
fun BranchScreen(
    navigator: DestinationsNavigator,
    viewModel: BranchScreenViewModel = hiltViewModel()
) {

    val branchesListState = viewModel.branchesListState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.retrieveBranchesList()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Cabang") },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Tambahkan Cabang"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        branchesListState.value.DisplayResult(
            modifier = Modifier.padding(12.dp),
            onLoading = { SharedLoadingScreen() },
            onSuccess = { branchesList ->
                LazyColumn(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    items(branchesList) { branch ->
                        BranchCard(branchName = branch.name) {

                        }
                    }
                }
            },
            onError = {
                SharedErrorScreen(
                    onRetryAction = { viewModel.retrieveBranchesList() },
                    exceptionMessage = it
                )
            }
        )
    }

}