package com.rmaprojects.owner.domain.usecases

import com.rmaprojects.owner.domain.model.BranchData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CountMonthIncomeUseCase {
    operator fun invoke(
        branchDataList: List<BranchData>
    ): Int {
        val incomeList = branchDataList.map { it.incomeTotal ?: 0 }
        return incomeList.sum()
    }
}