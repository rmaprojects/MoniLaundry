package com.rmaprojects.core.domain.model

import android.os.Parcelable
import com.rmaprojects.core.common.LaundryType
import kotlinx.parcelize.Parcelize

@Parcelize
data class PricesData(
    val id: String?,
    val branchId: String?,
    val itemName: String,
    val price: Int,
    val laundryType: LaundryType
) : Parcelable
