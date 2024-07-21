package com.rmaprojects.owner.presentation.screens.details.branch

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.generated.owner.destinations.BranchDetailScreenDestinationNavArgs
import com.ramcosta.composedestinations.generated.owner.navArgs
import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.owner.domain.model.BranchData
import com.rmaprojects.owner.domain.usecases.OwnerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BranchDetailViewModel @Inject constructor(
    private val useCases: OwnerUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val navArgs =
        savedStateHandle.navArgs<BranchDetailScreenDestinationNavArgs>()

    private val _branchInfoState =
        MutableStateFlow<ResponseState<BranchData>>(ResponseState.Idle)
    val branchInfoState = _branchInfoState
        .asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ResponseState.Idle
        )

    private val _branchMutationState =
        MutableSharedFlow<ResponseState<Boolean>>()
    val branchMutationState = _branchMutationState
        .asSharedFlow()
        .shareIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            0
        )

    fun retrieveBranchInfo() {
        resetState()
        viewModelScope.launch {
            _branchInfoState.emitAll(
                useCases.branchUseCases.getBranchInfo(navArgs.branchId ?: return@launch)
            )
        }
    }

    fun updateBranch(
        newBranchName: String,
        newLongitude: Float,
        newLatitude: Float,
        newImageUrl: String? = ""
    ) {
        viewModelScope.launch {
            useCases.branchUseCases.updateBranch(
                navArgs.branchId ?: return@launch,
                newBranchName,
                newLongitude,
                newLatitude,
                newImageUrl ?: ""
            )
        }
    }

    fun addBranch(
        branchName: String,
        longitude: Float,
        latitude: Float,
        imageUrl: String? = "",
        employeeId: String
    ) {
        viewModelScope.launch {
            _branchMutationState.emitAll(
                useCases.branchUseCases.addBranch(
                    branchName,
                    longitude,
                    latitude,
                    imageUrl,
                    employeeId
                )
            )
        }
    }

    fun deleteBranch() {
        viewModelScope.launch {
            _branchMutationState.emitAll(
                useCases.branchUseCases.deleteBranch(
                    navArgs.branchId ?: return@launch
                )
            )
        }
    }

    private fun resetState() {
        viewModelScope.launch {
            _branchInfoState.emit(ResponseState.Idle)
        }
    }


}