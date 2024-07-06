package com.rmaprojects.core.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmployeeDto(
    @SerialName("username") val username: String,
    @SerialName("email") val email: String? = "",
    @SerialName("role") val role: String? = "employee",
    @SerialName("id") val id: String? = "",
    @SerialName("employee_details") val employeeDetails: EmployeeDetailsDto,
)

@Serializable
data class EmployeeDetailsDto(
    @SerialName("full_name") val fullName: String,
    @SerialName("date_of_birth") val dateOfBirth: String,
    @SerialName("living_place") val livingPlace: String,
    @SerialName("id") val id: String = "",
    @SerialName("branch_owner_id") val ownerBranchId: String? = "",
    @SerialName("branch_id") val branchId: String? = "",
    @SerialName("created_at") val createdAt: String = "",
    @SerialName("tbl_branch") val branchDetail: BranchDto? = null
)