package com.rmaprojects.owner.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmployeeData(
    val employeeId: String,
    val fullName: String,
    val joinedAt: String,
    val dateOfBirth: String,
    val livingPlace: String,
    val username: String? = "",
    val branchId: String? = "",
    val branchName: String? = ""
) : Parcelable
