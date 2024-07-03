package com.rmaprojects.core.data.source.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PricesDto(
    @SerialName("items")
    val itemName: String,
    @SerialName("price")
    val price: Int,
    @SerialName("type")
    val type: String,
    @SerialName("branch_id")
    val branchId: String,
    @SerialName("id")
    val id: String = ""
)