package com.rmaprojects.owner.utils

import com.rmaprojects.core.common.LaundryType
import com.rmaprojects.core.data.source.remote.model.BranchDto
import com.rmaprojects.core.data.source.remote.model.EmployeeDetailsDto
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

fun EmployeeDetailsDto.mapToEmployeeData(): EmployeeData {
    return EmployeeData(
        this.id,
        this.fullName,
        this.createdAt,
        this.dateOfBirth,
        this.livingPlace,
        branchId = this.branchId,
        branchName = this.branchDetail?.name
    )
}

fun BranchDto.mapToBranchData(): BranchData {
    val totalIncome = this.laundryHistory?.sumOf { it.details!!.sumOf { it.totalPrice } } ?: 0
    return BranchData(
        this.name,
        this.id,
        this.employees?.map { it.mapToEmployeeData() },
        this.pricesList?.map { it.mapToPrice() },
        longitude = this.longitude,
        latitude = this.latitude,
        totalIncome = totalIncome,
        imageUrl = this.imageUrl,
    )
}

