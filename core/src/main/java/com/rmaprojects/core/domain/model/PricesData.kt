package com.rmaprojects.core.domain.model

import com.rmaprojects.core.common.LaundryType

data class PricesData(
    val itemName: String,
    val price: Int,
    val laundryType: LaundryType
)
