package com.rmaprojects.core.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerDto(
    @SerialName("id") val id: String,
    @SerialName("username") val username: String,
    @SerialName("role") val role: String,
    @SerialName("email") val email: String,
    @SerialName("tbl_owner") val ownerDetails: OwnerDetailsDto
)

@Serializable
data class OwnerDetailsDto(
    @SerialName("full_name") val name: String,
    @SerialName("id") val id: String? = "",
    @SerialName("created_at") val createdAt: String = "",
)
