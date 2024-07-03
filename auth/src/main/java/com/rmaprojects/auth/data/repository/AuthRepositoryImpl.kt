package com.rmaprojects.auth.data.repository

import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.auth.domain.repository.AuthRepository
import com.rmaprojects.core.common.Roles
import com.rmaprojects.core.domain.repository.CoreAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val coreAuthRepository: CoreAuthRepository
): AuthRepository {

    override fun signIn(username: String, password: String): Flow<ResponseState<Boolean>> = flow {
        emit(ResponseState.Loading)
        try {
            if (coreAuthRepository.signIn(username, password).isSuccess) {
                emit(ResponseState.Success(true))
            } else {
                emit(ResponseState.Error("Unable to sign in, check your password or username"))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun signUp(
        fullName: String,
        username: String,
        password: String
    ): Flow<ResponseState<Boolean>> = flow {
        emit(ResponseState.Loading)
        try {
            val result = coreAuthRepository.signUpOwner(username, password, Roles.Owner(fullName))
            if (result.isSuccess) {
                emit(ResponseState.Success(true))
            } else {
                emit(ResponseState.Error(result.exceptionOrNull()?.message ?: "Error to create user"))
            }
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

}