package com.rmaprojects.owner.domain.model

import android.os.Parcelable
import com.rmaprojects.core.domain.model.PricesData
import kotlinx.parcelize.Parcelize

@Parcelize
data class BranchData(
    val name: String,
    val branchId: String,
    val employeeList: List<EmployeeData>? = emptyList(),
    val productList: List<PricesData>? = emptyList(),
    val longitude: Float? = null,
    val latitude: Float? = null,
    val totalIncome: Int,
    val imageUrl: String? = null
) : Parcelable
