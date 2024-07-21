package com.rmaprojects.owner.presentation.screens.details.employee

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Person4
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.generated.owner.destinations.BranchPickerBottomSheetDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.result.onResult
import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.core.common.Roles
import com.rmaprojects.core.presentation.components.GlobalAlertDialog
import com.rmaprojects.core.presentation.screen.SharedErrorScreen
import com.rmaprojects.core.presentation.screen.SharedLoadingScreen
import com.rmaprojects.owner.domain.model.BranchData
import com.rmaprojects.owner.navigation.OwnerNavigation
import com.rmaprojects.owner.presentation.screens.details.employee.content.AddEmployeeContent
import com.rmaprojects.owner.presentation.screens.details.employee.content.EditEmployeeContent

@OptIn(ExperimentalMaterial3Api::class)
@Destination<OwnerNavigation>
@Composable
fun EmployeeDetailScreen(
    resultRecipient: ResultRecipient<BranchPickerBottomSheetDestination, BranchData>,
    navigator: DestinationsNavigator,
    employeeId: String?,
    viewModel: EmployeeDetailViewModel = hiltViewModel()
) {

    var isShowAlertDialog by rememberSaveable {
        mutableStateOf(false)
    }

    val employeeDetailState =
        viewModel.employeeDetailState.collectAsStateWithLifecycle()
    val employeeMutationState =
        viewModel.employeeMutationState.collectAsState(initial = ResponseState.Idle)

    val isEditMode by rememberSaveable {
        mutableStateOf(employeeId != null)
    }

    var showDoneIcon by remember {
        mutableStateOf(false)
    }

    var branchId by remember {
        mutableStateOf<String?>(null)
    }

    var editedUsername by remember {
        mutableStateOf("")
    }

    var editedEmployeeData by remember {
        mutableStateOf(Roles.Employee("", "", ""))
    }

    LaunchedEffect(true) {
        if (isEditMode) {
            viewModel.retrieveEmployeeDetail()
        }
    }

    resultRecipient.onResult(
        onCancelled = {
            branchId = null
        },
        onValue = {
            if (isEditMode) {
                viewModel.assignEmployeeBranch(it.branchId)
            } else {
                branchId = it.branchId
            }
        }
    )

    val snackbarHostState = SnackbarHostState()

    employeeMutationState.value.DisplayResult(
        onLoading = {
            LaunchedEffect(true) {
                snackbarHostState.showSnackbar("Loading...")
            }
        },
        onSuccess = {
            LaunchedEffect(true) {
                snackbarHostState.showSnackbar("Berhasil!")
            }
            navigator.navigateUp()
        },
        onError = {
            LaunchedEffect(true) {
                snackbarHostState.showSnackbar(it)
            }
        }
    )

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = { Text(text = if (isEditMode) "Edit Karyawan" else "Tambahkan Karyawan") },
                actions = {
                    if (isEditMode) {
                        if (showDoneIcon) {
                            IconButton(onClick = {
                                viewModel.editEmployee(
                                    editedUsername,
                                    editedEmployeeData
                                )
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = "Done Editing"
                                )
                            }
                        }
                        if (!showDoneIcon) {
                            IconButton(onClick = {
                                viewModel.deleteEmployee()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Done Editing",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Person4,
                contentDescription = "Person Profile",
                modifier = Modifier.size(128.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            if (isEditMode) {
                employeeDetailState.value.DisplayResult(
                    onLoading = { SharedLoadingScreen() },
                    onSuccess = { employee ->
                        val retrievedEmployeeData = Roles.Employee(
                            employee.fullName,
                            employee.dateOfBirth,
                            employee.livingPlace
                        )
                        EditEmployeeContent(
                            employee,
                            onSomethingChanged = { username, employeeData ->
                                showDoneIcon =
                                    username != employee.username || employeeData != retrievedEmployeeData
                                editedUsername = username
                                editedEmployeeData = employeeData
                            },
                            onBranchClicked = {
                                navigator.navigate(BranchPickerBottomSheetDestination)
                            }
                        )
                    },
                    onError = {
                        SharedErrorScreen(
                            onRetryAction = { viewModel.retrieveEmployeeDetail() },
                            exceptionMessage = it
                        )
                    }
                )
            } else {
                AddEmployeeContent(
                    branchData = branchId,
                    onAddEmployeeClick = { username, password, employeeData ->
                        viewModel.addEmployee(username, password, branchId ?: "", employeeData)
                    },
                    onAddBranchClick = {
                        navigator.navigate(BranchPickerBottomSheetDestination)
                    }
                )
            }
        }

        if (isShowAlertDialog) {
            GlobalAlertDialog(
                title = "Hapus Karyawan?",
                desc = "Aksi ini tidak dapat dipulihkan",
                confirmButton = {
                    Button(onClick = { viewModel.deleteEmployee() }) {
                        Text(text = "Ya, Hapus")
                    }
                },
                dismissButton = {
                    Button(onClick = { isShowAlertDialog = false }) {
                        Text(text = "Tidak jadi")
                    }
                },
                onDismiss = {
                    isShowAlertDialog = false
                }
            )
        }
    }

}