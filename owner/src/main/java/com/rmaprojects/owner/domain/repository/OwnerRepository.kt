package com.rmaprojects.owner.domain.repository

import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.core.common.Roles
import com.rmaprojects.core.data.source.remote.model.PricesDto
import com.rmaprojects.core.domain.model.PricesData
import com.rmaprojects.owner.domain.model.BranchData
import com.rmaprojects.owner.domain.model.EmployeeData
import kotlinx.coroutines.flow.Flow

interface OwnerRepository {
    fun addEmployee(
        username: String,
        email: String,
        password: String,
        employeeData: Roles.Employee
    ): Flow<ResponseState<Boolean>>
    fun editEmployee(
        employeeId: String,
        newUsername: String,
        newEmployeeData: Roles.Employee
    ): Flow<ResponseState<Boolean>>
    fun getAllEmployee(): Flow<ResponseState<List<EmployeeData>>>
    fun getAllEmployeeInfo(): Flow<ResponseState<EmployeeData>>
    fun deleteEmployee(employeeId: String): Flow<ResponseState<Boolean>>
    fun addBranch(
        longitude: Float, latitude: Float, imageUrl: String
    )
    fun getAllBranchInfo(): Flow<ResponseState<List<BranchData>>>
    fun getAllBranchInfoWithLaundryHistory(
        dateFrom: String,
        dateTo: String
    ): Flow<ResponseState<List<BranchData>>>
    fun getBranchInfo(branchId: String): Flow<ResponseState<BranchData>>
    fun deleteBranch(branchId: String): Flow<ResponseState<Boolean>>
    fun addPrices(
        pricesList: List<PricesData>
    )
    fun updatePrices(
        branchId: String,
        priceList: List<PricesData>
    )
    fun deletePrices(
        branchId: String,
        pricesId: String
    )
    fun getAllPrices(): Flow<List<PricesData>>
}