package com.rmaprojects.core.domain.model

import com.rmaprojects.core.common.types.LaundryType

data class PricesData(
    val id: String?,
    val branchId: String?,
    val itemName: String,
    val price: Int,
    val laundryType: LaundryType
)
