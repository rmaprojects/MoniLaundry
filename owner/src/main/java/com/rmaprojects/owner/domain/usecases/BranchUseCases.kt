package com.rmaprojects.owner.domain.usecases

import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.core.domain.model.PricesData
import com.rmaprojects.owner.domain.model.BranchData
import com.rmaprojects.owner.domain.model.EmployeeData
import com.rmaprojects.owner.domain.repository.OwnerRepository
import kotlinx.coroutines.flow.Flow

class BranchUseCases(private val repository: OwnerRepository) {

    fun getAllBranch(
        dateFrom: String? = "",
        dateTo: String? = ""
    ): Flow<ResponseState<List<BranchData>>> {
        return if (dateFrom.isNullOrEmpty() && dateTo.isNullOrEmpty()) repository.getAllBranchInfo() else repository.getAllBranchInfoWithLaundryHistory(
            dateFrom,
            dateTo
        )
    }

    fun addBranch(
        name: String,
        longitude: Float,
        latitude: Float,
        imageUrl: String? = "",
        employeeId: String? = null
    ): Flow<ResponseState<Boolean>> {
        return repository.addBranch(name, longitude, latitude, imageUrl ?: "", employeeId)
    }

    fun deleteBranch(branchId: String): Flow<ResponseState<Boolean>> {
        return repository.deleteBranch(branchId)
    }

    fun updateBranch(
        branchId: String,
        newBranchName: String,
        newLongitude: Float,
        newLatitude: Float,
        newImageUrl: String
    ): Flow<ResponseState<Boolean>> {
        return repository.editBranchInfo(
            branchId,
            newBranchName,
            newLongitude,
            newLatitude,
            newImageUrl
        )
    }

    fun getBranchInfo(branchId: String): Flow<ResponseState<BranchData>> {
        return repository.getBranchInfo(branchId)
    }

    fun getLaundryPricesFromBranch(branchId: String): Flow<ResponseState<List<PricesData>>> {
        return repository.getAllPrices(branchId)
    }

    fun getEmployeeListFromBranch(branchId: String): Flow<ResponseState<List<EmployeeData>>> {
        return repository.getAllEmployeeInBranch(branchId)
    }

}