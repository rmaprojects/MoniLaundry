package com.rmaprojects.core.data.repository

import com.rmaprojects.core.common.Roles
import com.rmaprojects.core.data.source.remote.UserRemoteDatasource
import com.rmaprojects.core.domain.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userRemoteDatasource: UserRemoteDatasource,
): AuthRepository {
    override suspend fun signInUser(email: String, password: String) {
        userRemoteDatasource.signIn(email, password)
    }

    override suspend fun signUpOwner(
        email: String,
        password: String,
        username: String,
        ownerData: Roles.Owner
    ) {
        userRemoteDatasource.signUpOwner(email, password, username, ownerData)
    }

    override suspend fun signEmployee(
        username: String,
        email: String,
        password: String,
        employee: Roles.Employee
    ) {
        userRemoteDatasource.signEmployee(username, email, password, employee)
    }

}