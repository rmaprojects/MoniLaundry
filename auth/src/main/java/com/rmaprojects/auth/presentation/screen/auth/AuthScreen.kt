package com.rmaprojects.auth.presentation.screen.auth

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.rmaprojects.auth.navigation.AuthNavigation
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Destination<AuthNavigation>
@Composable
fun AuthScreen(
    isEmployee: Boolean
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = SnackbarHostState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Masuk")
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        AuthScreenContent(
            modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp),
            onError = {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        it, duration = SnackbarDuration.Long,
                    )
                }
            },
            onSuccess = {
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            },
            isEmployee = isEmployee
        )
    }
}