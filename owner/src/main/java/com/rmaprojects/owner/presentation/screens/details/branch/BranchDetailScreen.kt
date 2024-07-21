package com.rmaprojects.owner.presentation.screens.details.branch

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.generated.owner.destinations.BranchPickerBottomSheetDestination
import com.ramcosta.composedestinations.generated.owner.destinations.EmployeePickerBottomSheetDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.result.onResult
import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.core.presentation.screen.SharedErrorScreen
import com.rmaprojects.core.presentation.screen.SharedLoadingScreen
import com.rmaprojects.owner.domain.model.BranchData
import com.rmaprojects.owner.navigation.OwnerNavigation
import com.rmaprojects.owner.presentation.screens.details.branch.content.AddBranchContent
import com.rmaprojects.owner.presentation.screens.details.branch.content.EditBranchContent

@OptIn(ExperimentalMaterial3Api::class)
@Destination<OwnerNavigation>
@Composable
fun BranchDetailScreen(
    branchId: String?,
    branchDataResultRecipient: ResultRecipient<EmployeePickerBottomSheetDestination, BranchData>,
    navigator: DestinationsNavigator,
    viewModel: BranchDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val snackbarHostState = SnackbarHostState()

    val branchMutationState = viewModel.branchMutationState.collectAsStateWithLifecycle(
        ResponseState.Idle
    )
    val branchDetailState = viewModel.branchInfoState.collectAsStateWithLifecycle()

    val isEditMode by rememberSaveable {
        mutableStateOf(branchId != null)
    }

    var showDoneIcon by rememberSaveable {
        mutableStateOf(false)
    }

    val branchDetailInfo = rememberSaveable {
        mutableStateOf(
            BranchData("", "", emptyList(), emptyList(), null, null, 0)
        )
    }

    branchDataResultRecipient.onResult(
        onCancelled = {
            Toast.makeText(context, "Pengambilan harga dibatalkan", Toast.LENGTH_SHORT).show()
        },
        onValue = {
            branchDetailInfo.value = branchDetailInfo.value.copy(productList = it.productList)
        }
    )

    branchMutationState.value.DisplayResult(
        onLoading = {
            LaunchedEffect(true) {
                snackbarHostState.showSnackbar("Loading...")
            }
        },
        onSuccess = {
            viewModel.retrieveBranchInfo()
            LaunchedEffect(true) {
                snackbarHostState.showSnackbar("Success!")
            }
        },
        onError = {
            viewModel.retrieveBranchInfo()
            LaunchedEffect(true) {
                snackbarHostState.showSnackbar("Error: $it")
            }
        }
    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(text = if (isEditMode) "Edit Cabang" else "Tambah Cabang") },
                actions = {
                    if (isEditMode) {
                        if (showDoneIcon) {
                            IconButton(
                                onClick = {
//
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = "Done Editing"
                                )
                            }
                        }
                        if (!showDoneIcon) {
                            IconButton(onClick = {
                                viewModel.deleteBranch()
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
        },
    ) { innerPadding ->
        if (isEditMode) {
            branchDetailState.value.DisplayResult(
                modifier = Modifier.padding(innerPadding),
                onLoading = { SharedLoadingScreen() },
                onSuccess = { branch ->
                    branchDetailInfo.value = branch
                    EditBranchContent(
                        branchDetailData = branchDetailInfo.value,
                        onBranchDataChanged = { editedBranchData ->
                            branchDetailInfo.value = editedBranchData
                            showDoneIcon = editedBranchData != branchDetailInfo.value
                        }
                    )
                },
                onError = {
                    SharedErrorScreen(
                        onRetryAction = { viewModel.retrieveBranchInfo() },
                        exceptionMessage = it
                    )
                }
            )
        } else {
            AddBranchContent(
                onAddLocationClick = {
                    Toast.makeText(context, "Coming Soon", Toast.LENGTH_SHORT).show()
                },
                onSelectBranchClick = {
                    navigator.navigate(BranchPickerBottomSheetDestination)
                },
                branchDetailInfo = branchDetailInfo.value,
                onSubmitButtonClick = {

                },
                onDeleteProduct = {

                },
                onEditProduct = {

                }
            )
        }
    }
}