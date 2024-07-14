package com.rmaprojects.owner.domain.model

data class EmployeeData(
    val fullName: String,
    val joinedAt: String,
    val dateOfBirth: String,
    val livingPlace: String,
    val username: String? = "",
    val branchId: String? = "",
    val branchName: String? = ""
)
