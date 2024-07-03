package com.rmaprojects.auth.presentation.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.auth.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _authState = MutableStateFlow<ResponseState<Boolean>>(ResponseState.Idle)
    val authState = _authState.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ResponseState.Idle
        )

    fun signInUser(username: String, password: String) {
        viewModelScope.launch {
            _authState.emitAll(
                authRepository.signIn(username, password)
            )
        }
    }

    fun signUpUser(username: String, fullName: String, password: String) {
        viewModelScope.launch {
            _authState.emitAll(authRepository.signUp(fullName, username, password))
        }
    }

    fun resetState() {
        viewModelScope.launch {
            _authState.emit(ResponseState.Idle)
        }
    }

}