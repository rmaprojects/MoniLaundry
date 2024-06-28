package com.rmaprojects.core.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("created_at") val createdAt: String = ""
)