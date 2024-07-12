package com.rmaprojects.core.data.repository

import com.rmaprojects.core.data.source.remote.BranchRemoteDatasource
import com.rmaprojects.core.data.source.remote.model.BranchDto
import com.rmaprojects.core.data.source.remote.model.EmployeeDetailsDto
import com.rmaprojects.core.data.source.remote.model.PricesDto
import com.rmaprojects.core.domain.repository.CoreBranchRepository
import javax.inject.Inject

class CoreBranchRepositoryImpl @Inject constructor(
    private val branchRemoteDatasource: BranchRemoteDatasource
) : CoreBranchRepository {

    override suspend fun addBranch(
        longitude: Float,
        latitude: Float,
        imageUrl: String,
        name: String
    ): Result<Boolean> {
        try {
            branchRemoteDatasource.insertNewBranch(longitude, latitude, imageUrl, name)
            return Result.success(true)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun getAllBranch(): Result<List<BranchDto>> {
        return try {
            val result = branchRemoteDatasource.getAllStoreBranch()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun editBranch(
        branchId: String,
        newLongitude: Float,
        newLatitude: Float,
        newName: String,
        newImageUrl: String?
    ): Result<Boolean> {
        return try {
            branchRemoteDatasource.updateBranch(branchId, newName, newLongitude, newLatitude, newImageUrl)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllBranchWithOrderHistory(
        orderRangeFrom: String?,
        orderRangeTo: String?
    ): Result<List<BranchDto>> {
        return try {
            val result = branchRemoteDatasource.getAllBranchWithOrderHistory(
                orderRangeFrom, orderRangeTo
            )
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun deleteBranch(branchId: String): Result<Boolean> {
        return try {
            branchRemoteDatasource.deleteBranch(branchId)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getBranchDetails(branchId: String): Result<BranchDto> {
        return try {
            val result = branchRemoteDatasource.getStoreBranchInfo(branchId)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getBranchEmployeeList(branchId: String): Result<List<EmployeeDetailsDto>> {
        return try {
            val result = branchRemoteDatasource.getEmployeeList(branchId)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getBranchEmployeeDetail(employeeId: String): Result<EmployeeDetailsDto> {
        return try {
            val result = branchRemoteDatasource.getEmployeeDetail(employeeId)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getEmployeeList(): Result<List<EmployeeDetailsDto>> {
        return try {
            val result = branchRemoteDatasource.getEmployeeList()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun editBranchEmployee(
        employeeId: String,
        newBranchId: String
    ): Result<Boolean> {
        return try {
            branchRemoteDatasource.editEmployee(employeeId, newBranchId)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllBranchPrices(
        branchId: String
    ): Result<List<PricesDto>> {
        return try {
            val result = branchRemoteDatasource.getAllPrices(branchId)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addBranchPrices(
        pricesList: List<PricesDto>
    ): Result<Boolean> {
        return try {
            branchRemoteDatasource.insertNewPrices(pricesList)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateNewPrices(
        branchId: String,
        pricesList: List<PricesDto>
    ): Result<Boolean> {
        return try {
            branchRemoteDatasource.updateNewPrices(branchId, pricesList)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deletePrices(branchId: String, pricesId: Int): Result<Boolean> {
        return try {
            branchRemoteDatasource.deletePrices(branchId, pricesId)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}