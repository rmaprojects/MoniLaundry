package com.rmaprojects.owner.presentation.screens.details.employee

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.generated.owner.destinations.EmployeeDetailScreenDestinationNavArgs
import com.ramcosta.composedestinations.generated.owner.navArgs
import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.core.common.Roles
import com.rmaprojects.owner.domain.model.EmployeeData
import com.rmaprojects.owner.domain.usecases.OwnerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeDetailViewModel @Inject constructor(
    private val ownerUseCases: OwnerUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val navArgs = savedStateHandle.navArgs<EmployeeDetailScreenDestinationNavArgs>()

    private val _employeeDetailState =
        MutableStateFlow<ResponseState<EmployeeData>>(ResponseState.Idle)
    val employeeDetailState = _employeeDetailState
        .asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ResponseState.Idle
        )

    fun retrieveEmployeeDetail() {
        resetAllState()
        viewModelScope.launch {
            _employeeDetailState.emitAll(
                ownerUseCases.employeeUseCases.getEmployeeInfo(navArgs.employeeId ?: return@launch)
            )
        }
    }

    private val _employeeMutationState =
        MutableSharedFlow<ResponseState<Boolean>>()
    val employeeMutationState = _employeeMutationState
        .asSharedFlow()
        .shareIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
        )

    fun addEmployee(
        username: String,
        password: String,
        branchId: String,
        employeeData: Roles.Employee
    ) {
        resetAllState()
        viewModelScope.launch {
            _employeeMutationState.emitAll(
                ownerUseCases.employeeUseCases.addEmployee(
                    username,
                    password,
                    branchId,
                    employeeData
                )

            )
        }
    }

    fun editEmployee(
        username: String,
        employeeData: Roles.Employee
    ) {
        resetAllState()
        viewModelScope.launch {
            _employeeMutationState.emitAll(
                ownerUseCases.employeeUseCases.editEmployee(
                    navArgs.employeeId ?: return@launch,
                    username,
                    employeeData
                )
            )
        }
    }

    fun deleteEmployee() {
        resetAllState()
        viewModelScope.launch {
            _employeeMutationState.emitAll(
                ownerUseCases.employeeUseCases.deleteEmployee(navArgs.employeeId ?: return@launch)
            )
        }
    }

    fun assignEmployeeBranch(
        branchId: String,
        employeeId: String = navArgs.employeeId ?: ""
    ) {
        viewModelScope.launch {
            ownerUseCases.employeeUseCases.assignEmployeeBranch(employeeId, branchId).collect()
                .let {
                    resetAllState()
                    retrieveEmployeeDetail()
                }
        }
    }

    private fun resetAllState() {
        viewModelScope.launch {
            _employeeMutationState.emit(ResponseState.Idle)
            _employeeDetailState.emit(ResponseState.Idle)
        }
    }

}
