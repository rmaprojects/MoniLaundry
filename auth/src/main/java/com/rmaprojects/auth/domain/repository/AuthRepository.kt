package com.rmaprojects.auth.domain.repository

import com.rmaprojects.apirequeststate.ResponseState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signIn(username: String, password: String): Flow<ResponseState<Boolean>>
    fun signUp(fullName: String, username: String, password: String): Flow<ResponseState<Boolean>>
}