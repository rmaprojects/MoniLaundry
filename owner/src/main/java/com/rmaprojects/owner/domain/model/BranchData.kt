package com.rmaprojects.owner.domain.model

data class BranchData(
    val name: String,
    val branchId: String,
    val employeeList: List<EmployeeData>? = emptyList(),
    val longitude: Float? = null,
    val latitude: Float? = null,
    val incomeTotal: Int? = null
)
