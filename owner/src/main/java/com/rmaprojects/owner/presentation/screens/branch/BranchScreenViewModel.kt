package com.rmaprojects.owner.presentation.screens.branch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.owner.domain.model.BranchData
import com.rmaprojects.owner.domain.usecases.OwnerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BranchScreenViewModel @Inject constructor(
    private val ownerUseCases: OwnerUseCases
): ViewModel() {

    private val _branchesListState = MutableStateFlow<ResponseState<List<BranchData>>>(ResponseState.Idle)
    val branchesListState = _branchesListState.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ResponseState.Idle
        )

    fun retrieveBranchesList() {
        resetState()
        viewModelScope.launch {
            _branchesListState.emitAll(
                ownerUseCases.branchUseCases.getAllBranch()
            )
        }
    }

    private fun resetState() {
        viewModelScope.launch {
            _branchesListState.emit(ResponseState.Idle)
        }
    }

}