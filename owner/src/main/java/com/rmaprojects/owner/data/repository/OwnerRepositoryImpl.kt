package com.rmaprojects.owner.data.repository

import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.core.common.Roles
import com.rmaprojects.core.domain.model.PricesData
import com.rmaprojects.core.domain.repository.CoreAuthRepository
import com.rmaprojects.core.domain.repository.CoreBranchRepository
import com.rmaprojects.owner.domain.model.BranchData
import com.rmaprojects.owner.domain.model.EmployeeData
import com.rmaprojects.owner.domain.repository.OwnerRepository
import com.rmaprojects.owner.utils.mapToBranchData
import com.rmaprojects.owner.utils.mapToDto
import com.rmaprojects.owner.utils.mapToEmployeeData
import com.rmaprojects.owner.utils.mapToPrice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.math.sign

class OwnerRepositoryImpl @Inject constructor(
    private val coreBranchRepository: CoreBranchRepository,
    private val coreUserRepository: CoreAuthRepository,
) : OwnerRepository {

    override fun addEmployee(
        username: String,
        password: String,
        branchId: String,
        employeeData: Roles.Employee
    ): Flow<ResponseState<Boolean>> = flow {
        emit(ResponseState.Loading)
        try {
            val signEmployeeResult = coreUserRepository.signEmployee(
                username,
                password,
                employeeData
            )
            if (signEmployeeResult.isSuccess) {
                signEmployeeResult.getOrNull().let {
                    coreBranchRepository.editBranchEmployee(it ?: "", branchId)
                }
                emit(ResponseState.Success(true))
            } else {
                emit(ResponseState.Error(signEmployeeResult.exceptionOrNull()?.message.toString()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun editEmployee(
        employeeId: String,
        newUsername: String,
        newEmployeeData: Roles.Employee
    ): Flow<ResponseState<Boolean>> = flow {
        emit(ResponseState.Loading)
        try {
            val result = coreUserRepository.updateEmployeeInfo(
                employeeId, newUsername, newEmployeeData
            )
            if (result.isSuccess) {
                emit(ResponseState.Success(true))
            } else {
                emit(ResponseState.Error(result.exceptionOrNull()?.message.toString()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun getAllEmployee(): Flow<ResponseState<List<EmployeeData>>> = flow {
        emit(ResponseState.Loading)
        try {
            val result = coreBranchRepository.getEmployeeList()
            if (result.isSuccess) {
                val mappedList = result.getOrElse { emptyList() }.map { it.mapToEmployeeData() }
                emit(ResponseState.Success(mappedList))
            } else {
                emit(ResponseState.Error(result.exceptionOrNull()?.message.toString()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun getAllEmployeeInBranch(
        branchId: String
    ): Flow<ResponseState<List<EmployeeData>>> = flow {
        emit(ResponseState.Loading)
        try {
            val result = coreBranchRepository.getBranchEmployeeList(branchId)
            if (result.isSuccess) {
                val mappedList = result.getOrElse { emptyList() }.map { it.mapToEmployeeData() }
                emit(ResponseState.Success(mappedList))
            } else {
                emit(ResponseState.Error(result.exceptionOrNull()?.message.toString()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun getEmployeeInfo(
        employeeId: String
    ): Flow<ResponseState<EmployeeData>> = flow {
        emit(ResponseState.Loading)
        try {
            val result = coreBranchRepository.getBranchEmployeeDetail(employeeId)
            if (result.isSuccess) {
                result.getOrNull()?.let {
                    emit(ResponseState.Success(it.mapToEmployeeData()))
                }
            } else {
                emit(ResponseState.Error(result.exceptionOrNull()?.message.toString()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun deleteEmployee(employeeId: String): Flow<ResponseState<Boolean>> = flow {
        emit(ResponseState.Loading)
        try {
            val result = coreUserRepository.deleteUser(employeeId)
            if (result.isSuccess) {
                result.getOrNull()?.let {
                    emit(ResponseState.Success(true))
                }
            } else {
                emit(ResponseState.Error(result.exceptionOrNull()?.message.toString()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun editEmployeeBranch(
        employeeId: String,
        branchId: String
    ): Flow<ResponseState<Boolean>> = flow {
        emit(ResponseState.Loading)
        try {
            val result = coreBranchRepository.editBranchEmployee(employeeId, branchId)
            if (result.isSuccess) {
                result.getOrNull()?.let {
                    emit(ResponseState.Success(true))
                }
            } else {
                emit(ResponseState.Error(result.exceptionOrNull()?.message.toString()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun addBranch(
        name: String,
        longitude: Float,
        latitude: Float,
        imageUrl: String,
        employeeId: String?,
    ): Flow<ResponseState<Boolean>> = flow {
        emit(ResponseState.Loading)
        try {
            val result = coreBranchRepository.addBranch(
                longitude, latitude, imageUrl, name
            )
            if (result.isSuccess) {
                if (employeeId != null) {
                    result.getOrNull()?.let { branchId ->
                        coreBranchRepository.editBranchEmployee(employeeId, branchId)
                    }
                }
                emit(ResponseState.Success(true))
            } else {
                emit(ResponseState.Error(result.exceptionOrNull()?.message.toString()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun getAllBranchInfo(): Flow<ResponseState<List<BranchData>>> = flow {
        emit(ResponseState.Loading)
        try {
            val result = coreBranchRepository.getAllBranch()
            if (result.isSuccess) {
                val mappedList = result.getOrElse { emptyList() }.map { it.mapToBranchData() }
                emit(ResponseState.Success(mappedList))
            } else {
                emit(ResponseState.Error(result.exceptionOrNull()?.message.toString()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun getAllBranchInfoWithLaundryHistory(
        dateFrom: String?,
        dateTo: String?
    ): Flow<ResponseState<List<BranchData>>> = flow {
        emit(ResponseState.Loading)
        try {
            val result = coreBranchRepository.getAllBranchWithOrderHistory(dateFrom, dateTo)
            if (result.isSuccess) {
                val mappedList = result.getOrElse { emptyList() }.map { it.mapToBranchData() }

                emit(ResponseState.Success(mappedList))
            } else {
                emit(ResponseState.Error(result.exceptionOrNull()?.message.toString()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun getBranchInfo(branchId: String): Flow<ResponseState<BranchData>> = flow {
        emit(ResponseState.Loading)
        try {
            val result = coreBranchRepository.getBranchDetails(branchId)
            if (result.isSuccess) {
                result.getOrNull()?.let {
                    emit(ResponseState.Success(it.mapToBranchData()))
                }
            } else {
                emit(ResponseState.Error(result.exceptionOrNull()?.message.toString()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun editBranchInfo(
        branchId: String,
        newBranchName: String,
        newLongitude: Float,
        newLatitude: Float,
        newImageUrl: String?
    ): Flow<ResponseState<Boolean>> = flow {
        emit(ResponseState.Loading)
        try {
            val result = coreBranchRepository.editBranch(
                branchId,
                newLongitude,
                newLatitude,
                newBranchName,
                newImageUrl
            )
            if (result.isSuccess) {
                result.getOrNull()?.let {
                    emit(ResponseState.Success(true))
                }
            } else {
                emit(ResponseState.Error(result.exceptionOrNull()?.message.toString()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun deleteBranch(branchId: String): Flow<ResponseState<Boolean>> = flow {
        emit(ResponseState.Loading)
        try {
            val result = coreBranchRepository.deleteBranch(branchId)
            if (result.isSuccess) {
                emit(ResponseState.Success(true))
            } else {
                emit(ResponseState.Error(result.exceptionOrNull()?.message.toString()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun addPrices(pricesList: List<PricesData>): Flow<ResponseState<Boolean>> = flow {
        emit(ResponseState.Loading)
        try {
            val mappedPriceList = pricesList.map { it.mapToDto() }
            val result = coreBranchRepository.addBranchPrices(mappedPriceList)
            if (result.isSuccess) {
                emit(ResponseState.Success(true))
            } else {
                emit(ResponseState.Error(result.exceptionOrNull()?.message.toString()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun updatePrices(
        branchId: String,
        priceList: List<PricesData>
    ): Flow<ResponseState<Boolean>> = flow {
        emit(ResponseState.Loading)
        try {
            val mappedPriceList = priceList.map { it.mapToDto() }
            val result = coreBranchRepository.updateNewPrices(
                branchId, mappedPriceList
            )
            if (result.isSuccess) {
                emit(ResponseState.Success(true))
            } else {
                emit(ResponseState.Error(result.exceptionOrNull()?.message.toString()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun deletePrices(branchId: String, pricesId: Int): Flow<ResponseState<Boolean>> =
        flow {
            emit(ResponseState.Loading)
            try {
                val result = coreBranchRepository.deletePrices(branchId, pricesId)
                if (result.isSuccess) {
                    emit(ResponseState.Success(true))
                } else {
                    emit(ResponseState.Error(result.exceptionOrNull()?.message.toString()))
                }
            } catch (e: Exception) {
                emit(ResponseState.Error(e.message.toString()))
            }
        }

    override fun getAllPrices(branchId: String): Flow<ResponseState<List<PricesData>>> = flow {
        emit(ResponseState.Loading)
        try {
            val result = coreBranchRepository.getAllBranchPrices(branchId)
            if (result.isSuccess) {
                val mappedPriceData = result.getOrElse { emptyList() }.map { it.mapToPrice() }
                emit(ResponseState.Success(mappedPriceData))
            } else {
                emit(ResponseState.Error(result.exceptionOrNull()?.message.toString()))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

}