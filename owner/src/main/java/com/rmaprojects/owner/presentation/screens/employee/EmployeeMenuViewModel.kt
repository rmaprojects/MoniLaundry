package com.rmaprojects.owner.presentation.screens.employee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.owner.domain.model.EmployeeData
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
class EmployeeMenuViewModel @Inject constructor(
    private val ownerUseCases: OwnerUseCases
) : ViewModel() {

    private val _employeeListState =
        MutableStateFlow<ResponseState<List<EmployeeData>>>(ResponseState.Idle)
    val employeeListState = _employeeListState.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ResponseState.Idle
        )

    fun retrieveAllEmployee() {
        resetState()
        viewModelScope.launch {
            _employeeListState.emitAll(ownerUseCases.employeeUseCases.getAllEmployee())
        }
    }

    private fun resetState() {
        viewModelScope.launch {
            _employeeListState.emit(ResponseState.Idle)
        }
    }

}