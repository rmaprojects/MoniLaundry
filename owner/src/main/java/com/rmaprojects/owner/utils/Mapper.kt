package com.rmaprojects.owner.utils

import com.rmaprojects.core.common.types.LaundryType
import com.rmaprojects.core.data.source.remote.model.BranchDto
import com.rmaprojects.core.data.source.remote.model.EmployeeDetailsDto
import com.rmaprojects.core.data.source.remote.model.EmployeeDto
import com.rmaprojects.core.data.source.remote.model.PricesDto
import com.rmaprojects.core.domain.model.PricesData
import com.rmaprojects.owner.domain.model.BranchData
import com.rmaprojects.owner.domain.model.EmployeeData

fun PricesDto.mapToPrice(): PricesData {
    return PricesData(
        this.id,
        this.branchId,
        this.itemName,
        this.price,
        if (this.type == "single") LaundryType.SINGLE else LaundryType.PACKAGE,
    )
}

fun PricesData.mapToDto(): PricesDto {
    return PricesDto(
        this.itemName,
        this.price,
        this.laundryType.typeName,
        this.branchId,
        this.id
    )
}

fun EmployeeDto.mapToEmployeeData(): EmployeeData {
    return EmployeeData(
        this.employeeDetails.fullName,
        this.employeeDetails.createdAt,
        this.employeeDetails.dateOfBirth,
        this.employeeDetails.livingPlace,
        username = this.username,
        this.employeeDetails.branchId
    )
}

fun EmployeeDetailsDto.mapToEmployeeData(): EmployeeData {
    return EmployeeData(
        this.fullName,
        this.createdAt,
        this.dateOfBirth,
        this.livingPlace,
        branchId = this.branchId
    )
}

fun BranchDto.mapToBranchData(totalIncome: Int? = null): BranchData {
    return BranchData(
        this.name,
        this.id,
        this.employees?.map { it.mapToEmployeeData() },
        this.longitude,
        this.latitude,
        totalIncome
    )
}