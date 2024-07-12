package com.rmaprojects.auth.common

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.rmaprojects.core.data.source.local.LocalUserData

object AuthState {
    internal val mLoggedRole: MutableState<String?> = mutableStateOf(null)
    internal val mLoggedIn = mutableStateOf(LocalUserData.uuid != null)
    val loggedRole: State<String?> = mLoggedRole
    val isLoggedIn: State<Boolean> = mLoggedIn

    fun reset() {
        mLoggedRole.value = null
        mLoggedIn.value = false
    }
}
