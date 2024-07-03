package com.rmaprojects.core.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.Serial

@Serializable
data class OwnerDto(
    @SerialName("id") val id: String,
    @SerialName("username") val username: String,
    @SerialName("role") val role: String,
    @SerialName("email") val email: String,
    @SerialName("tbl_owner") val ownerDetails: OwnerDetails
)

@Serializable
data class OwnerDetails(
    @SerialName("id") val id: String,
    @SerialName("full_name") val name: String,
    @SerialName("created_at") val createdAt: String = "",
)
