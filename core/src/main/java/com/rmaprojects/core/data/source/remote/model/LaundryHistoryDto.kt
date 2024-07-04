package com.rmaprojects.core.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.Serial

@Serializable
data class LaundryHistoryDto(
    @SerialName("order_id") val orderId: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("name_customer") val customerName: String,
    @SerialName("address_customer") val customerAddress: String,
    @SerialName("status") val status: String,
    @SerialName("employee_id") val employeeId: String,
    @SerialName("branch_id") val branchId: String,
    @SerialName("tbl_history_details") val details: List<LaundryHistoryDetailsDto>,
)

@Serializable
data class LaundryHistoryDetailsDto(
    @SerialName("id") val id: Int,
    @SerialName("created_at") val createdAt: String,
    @SerialName("order_id") val orderId: String,
    @SerialName("item_id") val itemId: String,
    @SerialName("quantity") val quantity: String,
    @SerialName("total_price") val totalPrice: String,
    @SerialName("tbl_prices") val priceDetail: PricesDto
)