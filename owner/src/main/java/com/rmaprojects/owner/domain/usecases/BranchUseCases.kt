package com.rmaprojects.owner.domain.usecases

import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.core.domain.model.PricesData
import com.rmaprojects.owner.domain.model.BranchData
import com.rmaprojects.owner.domain.model.EmployeeData
import com.rmaprojects.owner.domain.repository.OwnerRepository
import kotlinx.coroutines.flow.Flow

class BranchUseCases(private val repository: OwnerRepository) {

    fun getAllBranch(): Flow<ResponseState<List<BranchData>>> {
        return repository.getAllBranchInfo()
    }

    fun addBranch(
        name: String,
        longitude: Float,
        latitude: Float,
        imageUrl: String? = ""
    ): Flow<ResponseState<Boolean>> {
        return repository.addBranch(name, longitude, latitude, imageUrl ?: "")
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
        return repository.editBranchInfo(branchId, newBranchName, newLongitude, newLatitude, newImageUrl)
    }

    fun getLaundryPricesFromBranch(branchId: String): Flow<ResponseState<List<PricesData>>> {
        return repository.getAllPrices(branchId)
    }

    fun getEmployeeListFromBranch(branchId: String): Flow<ResponseState<List<EmployeeData>>> {
        return repository.getAllEmployeeInBranch(branchId)
    }

}