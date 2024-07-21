package com.rmaprojects.core.domain.repository

import com.rmaprojects.core.common.Roles
import io.github.jan.supabase.gotrue.user.UserInfo

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
    ): Result<String>

    suspend fun logOut()

    suspend fun updateEmployeeInfo(
        employeeId: String,
        newUsername: String,
        newEmployeeData: Roles.Employee
    ): Result<Boolean>

    suspend fun deleteUser(
        userId: String
    ): Result<Boolean>
}