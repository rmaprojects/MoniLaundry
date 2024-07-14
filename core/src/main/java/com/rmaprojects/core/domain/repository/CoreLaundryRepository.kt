package com.rmaprojects.core.domain.repository

import com.rmaprojects.core.common.LaundryStatus
import com.rmaprojects.core.data.source.remote.model.LaundryHistoryDetailsDto
import com.rmaprojects.core.data.source.remote.model.LaundryHistoryDto

interface CoreLaundryRepository {
    suspend fun getLaundryHistory(): Result<List<LaundryHistoryDto>>
    suspend fun getOrderDetail(orderId: String): Result<LaundryHistoryDto>
    suspend fun insertLaundry(
        employeeId: String,
        branchId: String,
        status: LaundryStatus = LaundryStatus.PROCESS,
        customerName: String,
        customerAddress: String,
        orderList: List<LaundryHistoryDetailsDto>
    ): Result<Boolean>

    suspend fun changeLaundryStatus(
        orderId: String,
        newStatus: LaundryStatus
    ): Result<Boolean>

    suspend fun deleteLaundryOrderHistory(
        orderId: String
    ): Result<Boolean>

}