package com.rmaprojects.core.domain

import com.rmaprojects.core.common.Roles

interface CoreAuthRepository {

    suspend fun signIn(
        username: String,
        password: String
    ): Result<Boolean>

    suspend fun signUpOwner(
        username: String,
        password: String,
        ownerData: Roles.Owner
    ): Result<Boolean>

    suspend fun signEmployee(
        username: String,
        password: String,
        employee: Roles.Employee
    ): Result<Boolean>

    suspend fun logOut()
}