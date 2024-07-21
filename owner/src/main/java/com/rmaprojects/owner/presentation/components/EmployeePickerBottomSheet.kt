package com.rmaprojects.owner.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.bottomsheet.spec.DestinationStyleBottomSheet
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.rmaprojects.core.presentation.components.EmployeeCard
import com.rmaprojects.core.presentation.screen.SharedErrorScreen
import com.rmaprojects.core.presentation.screen.SharedLoadingScreen
import com.rmaprojects.owner.navigation.OwnerNavigation
import com.rmaprojects.owner.presentation.screens.employee.EmployeeMenuViewModel

@Destination<OwnerNavigation>(style = DestinationStyleBottomSheet::class)
@Composable
fun EmployeePickerBottomSheet(
    modifier: Modifier = Modifier,
    resultBackNavigator: ResultBackNavigator<String>,
    viewModel: EmployeeMenuViewModel = hiltViewModel()
) {

    val employeeListState = viewModel.employeeListState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.retrieveAllEmployee()
    }

    employeeListState.value.DisplayResult(
        modifier = Modifier.padding(24.dp),
        onLoading = {
            SharedLoadingScreen()
        },
        onSuccess = { branchList ->
            LazyColumn(modifier = modifier.padding(12.dp)) {
                item {
                    Text(text = "")
                    Spacer(modifier = Modifier.height(24.dp))
                }
                items(branchList) { employee ->
                    EmployeeCard(
                        employeeName = employee.fullName,
                        branchName = employee.branchName ?: "Belum dipekerjakan"
                    ) {
                        resultBackNavigator.navigateBack(employee.employeeId)
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