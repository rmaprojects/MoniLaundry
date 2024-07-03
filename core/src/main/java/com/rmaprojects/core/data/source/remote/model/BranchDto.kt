package com.rmaprojects.core.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BranchDto(
    @SerialName("longitude") val longitude: Float,
    @SerialName("latitude") val latitude: Float,
    @SerialName("owner_id") val ownerId: String,
    @SerialName("id") val id: String = "",
    @SerialName("image_url") val imageUrl: String? = "",
    @SerialName("created_at") val createdAt: String = ""
)