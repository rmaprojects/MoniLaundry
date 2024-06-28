package com.rmaprojects.core.domain

import com.rmaprojects.core.common.Roles

interface AuthRepository {
    suspend fun signInUser(
        email: String,
        password: String
    )
    suspend fun signUpOwner(
        email: String,
        password: String,
        username: String,
        ownerData: Roles.Owner
    )
    suspend fun signEmployee(
        username: String,
        email: String,
        password: String,
        employee: Roles.Employee
    )
}