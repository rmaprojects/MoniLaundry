package com.rmaprojects.core.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BranchDto(
    @SerialName("owner_id") val ownerId: String,
    @SerialName("name") val name: String,
    @SerialName("latitude") val latitude: Float? = 0f,
    @SerialName("longitude") val longitude: Float? = 0f,
    @SerialName("id") val id: String = "",
    @SerialName("image_url") val imageUrl: String? = "",
    @SerialName("created_at") val createdAt: String = "",
    @SerialName("tbl_employee") val employees: List<EmployeeDetailsDto>? = null,
    @SerialName("tbl_laundry_history") val laundryHistory: List<LaundryHistoryDto>? = null
)