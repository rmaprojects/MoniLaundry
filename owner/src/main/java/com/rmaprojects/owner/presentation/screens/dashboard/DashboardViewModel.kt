package com.rmaprojects.owner.presentation.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.core.utils.firstDayInMonth
import com.rmaprojects.core.utils.getCurrentDate
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
class DashboardViewModel @Inject constructor(
    private val useCases: OwnerUseCases
) : ViewModel() {

    private val _branchListState =
        MutableStateFlow<ResponseState<List<BranchData>>>(ResponseState.Idle)
    val branchListState = _branchListState
        .asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ResponseState.Idle
        )

    fun retrieveAllBranch(
        dateFrom: String = getCurrentDate().toString(),
        dateTo: String = getCurrentDate().firstDayInMonth().toString()
    ) {
        resetState()
        viewModelScope.launch {
            _branchListState.emitAll(
                useCases.branchUseCases.getAllBranch(dateFrom, dateTo)
            )
        }
    }

    private fun resetState() {
        viewModelScope.launch {
            _branchListState.emit(ResponseState.Idle)
        }
    }

    fun getMonthIncomeTotal(branchDataList: List<BranchData>): Int {
        return branchDataList.sumOf { it.totalIncome }
    }

}