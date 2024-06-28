package com.rmaprojects.core.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmployeeDto(
    @SerialName("id") val id: String,
    @SerialName("full_name") val fullName: String,
    @SerialName("date_of_birth") val dateOfBirth: String,
    @SerialName("living_place") val livingPlace: String,
    @SerialName("branch_owner_id") val ownerBranchId: String,
    @SerialName("branch_id") val branchId: String,
    @SerialName("created_at") val createdAt: String = "",
)