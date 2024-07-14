package com.rmaprojects.owner.presentation.screens.employee

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
import com.rmaprojects.core.presentation.components.EmployeeCard
import com.rmaprojects.core.presentation.screen.SharedErrorScreen
import com.rmaprojects.core.presentation.screen.SharedLoadingScreen
import com.rmaprojects.owner.navigation.OwnerNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Destination<OwnerNavigation>
@Composable
fun EmployeeMenuScreen(
    navigator: DestinationsNavigator,
    viewModel: EmployeeMenuViewModel = hiltViewModel()
) {

    val employeeListState = viewModel.employeeListState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.retrieveAllEmployee()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Karyawan") },
                actions = {
                    IconButton(
                        onClick = { }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Tambahkan Cabang"
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        employeeListState.value.DisplayResult(
            modifier = Modifier
                .padding(innerPadding)
                .padding(12.dp),
            onLoading = {
                SharedLoadingScreen()
            },
            onSuccess = { employeeList ->
                LazyColumn {
                    items(employeeList) { employee ->
                        EmployeeCard(employeeName = employee.fullName, branchName = employee.branchName) {

                        }
                    }
                }
            },
            onError = {
                SharedErrorScreen(
                    onRetryAction = { viewModel.retrieveAllEmployee() },
                    exceptionMessage = it
                )
            }
        )
    }
}