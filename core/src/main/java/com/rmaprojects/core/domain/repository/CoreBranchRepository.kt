package com.rmaprojects.core.domain.repository

import com.rmaprojects.core.data.source.remote.model.BranchDto
import com.rmaprojects.core.data.source.remote.model.EmployeeDetailsDto
import com.rmaprojects.core.data.source.remote.model.PricesDto

interface CoreBranchRepository {
    suspend fun getAllBranch(): Result<List<BranchDto>>
    suspend fun addBranch(
        longitude: Float, latitude: Float, imageUrl: String, name: String
    ): Result<String>

    suspend fun editBranch(
        branchId: String,
        newLongitude: Float,
        newLatitude: Float,
        newName: String,
        newImageUrl: String?
    ): Result<Boolean>

    suspend fun getAllBranchWithOrderHistory(
        orderRangeFrom: String? = "",
        orderRangeTo: String? = ""
    ): Result<List<BranchDto>>

    suspend fun deleteBranch(branchId: String): Result<Boolean>
    suspend fun getBranchDetails(branchId: String): Result<BranchDto>
    suspend fun getBranchEmployeeList(branchId: String): Result<List<EmployeeDetailsDto>>
    suspend fun getBranchEmployeeDetail(employeeId: String): Result<EmployeeDetailsDto>
    suspend fun getEmployeeList(): Result<List<EmployeeDetailsDto>>
    suspend fun editBranchEmployee(employeeId: String, newBranchId: String): Result<Boolean>
    suspend fun getAllBranchPrices(branchId: String): Result<List<PricesDto>>
    suspend fun addBranchPrices(pricesList: List<PricesDto>): Result<Boolean>
    suspend fun updateNewPrices(branchId: String, pricesList: List<PricesDto>): Result<Boolean>
    suspend fun deletePrices(branchId: String, pricesId: Int): Result<Boolean>
}