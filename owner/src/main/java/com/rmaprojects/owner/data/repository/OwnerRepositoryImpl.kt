package com.rmaprojects.owner.data.repository

import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.core.common.Roles
import com.rmaprojects.core.domain.model.PricesData
import com.rmaprojects.core.domain.repository.CoreAuthRepository
import com.rmaprojects.core.domain.repository.CoreBranchRepository
import com.rmaprojects.core.domain.repository.CoreLaundryRepository
import com.rmaprojects.owner.domain.model.BranchData
import com.rmaprojects.owner.domain.model.EmployeeData
import com.rmaprojects.owner.domain.repository.OwnerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OwnerRepositoryImpl @Inject constructor (
    private val coreLaundryRepository: CoreLaundryRepository,
    private val coreBranchRepository: CoreBranchRepository,
    private val coreUserRepository: CoreAuthRepository
): OwnerRepository {
    override fun addEmployee(
        username: String,
        email: String,
        password: String,
        employeeData: Roles.Employee
    ): Flow<ResponseState<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun editEmployee(
        employeeId: String,
        newUsername: String,
        newEmployeeData: Roles.Employee
    ): Flow<ResponseState<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun getAllEmployee(): Flow<ResponseState<List<EmployeeData>>> {
        TODO("Not yet implemented")
    }

    override fun getAllEmployeeInfo(): Flow<ResponseState<EmployeeData>> {
        TODO("Not yet implemented")
    }

    override fun deleteEmployee(employeeId: String): Flow<ResponseState<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun addBranch(longitude: Float, latitude: Float, imageUrl: String) {
        TODO("Not yet implemented")
    }

    override fun getAllBranchInfo(): Flow<ResponseState<List<BranchData>>> {
        TODO("Not yet implemented")
    }

    override fun getAllBranchInfoWithLaundryHistory(
        dateFrom: String,
        dateTo: String
    ): Flow<ResponseState<List<BranchData>>> {
        TODO("Not yet implemented")
    }

    override fun getBranchInfo(branchId: String): Flow<ResponseState<BranchData>> {
        TODO("Not yet implemented")
    }

    override fun deleteBranch(branchId: String): Flow<ResponseState<Boolean>> {
        TODO("Not yet implemented")
    }

    override fun addPrices(pricesList: List<PricesData>) {
        TODO("Not yet implemented")
    }

    override fun updatePrices(branchId: String, priceList: List<PricesData>) {
        TODO("Not yet implemented")
    }

    override fun deletePrices(branchId: String, pricesId: String) {
        TODO("Not yet implemented")
    }

    override fun getAllPrices(): Flow<List<PricesData>> {
        TODO("Not yet implemented")
    }

}