package com.rmaprojects.core.data.repository

import com.rmaprojects.core.common.types.LaundryStatus
import com.rmaprojects.core.data.source.remote.LaundryRemoteDatasource
import com.rmaprojects.core.data.source.remote.model.LaundryHistoryDetailsDto
import com.rmaprojects.core.data.source.remote.model.LaundryHistoryDto
import com.rmaprojects.core.domain.repository.CoreLaundryRepository
import javax.inject.Inject

class CoreLaundryRepositoryImpl @Inject constructor(
    private val remoteDatasource: LaundryRemoteDatasource
) : CoreLaundryRepository {
    override suspend fun getLaundryHistory(): Result<List<LaundryHistoryDto>> {
        return try {
            val result = remoteDatasource.getAllLaundryHistory()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getOrderDetail(orderId: String): Result<LaundryHistoryDto> {
        return try {
            val result = remoteDatasource.getLaundryHistory(orderId)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun insertLaundry(
        employeeId: String,
        branchId: String,
        status: LaundryStatus,
        customerName: String,
        customerAddress: String,
        orderList: List<LaundryHistoryDetailsDto>
    ): Result<Boolean> {
        return try {
            remoteDatasource.insertLaundryInput(
                employeeId,
                branchId,
                status,
                customerName,
                customerAddress,
                orderList
            )
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun changeLaundryStatus(
        orderId: String,
        newStatus: LaundryStatus
    ): Result<Boolean> {
        return try {
            if (remoteDatasource.checkIfOrderIdExists(orderId)) {
                remoteDatasource.changeLaundryStatus(orderId, newStatus)
                Result.success(true)
            } else {
                Result.failure(Exception("Order not exits"))
            }
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteLaundryOrderHistory(orderId: String): Result<Boolean> {
        return try {
            remoteDatasource.deleteLaundryOrderHistory(orderId)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}