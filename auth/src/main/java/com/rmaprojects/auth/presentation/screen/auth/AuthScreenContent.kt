package com.rmaprojects.auth.presentation.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rmaprojects.auth.presentation.components.RegisterComponent
import com.rmaprojects.auth.presentation.components.SignInComponent
import kotlinx.coroutines.launch
import com.rmaprojects.core.R as CoreRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreenContent(
    modifier: Modifier = Modifier,
    isEmployee: Boolean,
    onError: (String) -> Unit = {},
    onSuccess: () -> Unit = {},
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState { 2 }
    val selectedSignType = remember { derivedStateOf { pagerState.currentPage } }

    val authState = viewModel.authState.collectAsStateWithLifecycle()

    val isLoading = authState.value.isLoading()

    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Image(
                modifier = Modifier.size(256.dp),
                painter = painterResource(id = CoreRes.drawable.monilaundry_logo),
                contentDescription = "App Logo"
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = if (pagerState.currentPage == 0) "Masuk ke akun anda" else "Belum punya akun?",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(24.dp))
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                SegmentedButton(
                    enabled = !isLoading,
                    selected = selectedSignType.value == 0,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    },
                    shape = if (!isEmployee) CircleShape.copy(
                        topEnd = CornerSize(0.dp),
                        bottomEnd = CornerSize(0.dp),
                        topStart = CornerSize(12.dp),
                        bottomStart = CornerSize(12.dp),

                        ) else CircleShape,
                    icon = {}
                ) {
                    Text(text = "Sign In")
                }
                if (!isEmployee) {
                    SegmentedButton(
                        enabled = !isLoading,
                        selected = selectedSignType.value == 1,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(1)
                            }
                        },
                        shape = CircleShape.copy(
                            topStart = CornerSize(0.dp),
                            bottomStart = CornerSize(0.dp),
                            topEnd = CornerSize(12.dp),
                            bottomEnd = CornerSize(12.dp)
                        ),
                        icon = {}
                    ) {
                        Text(text = "Sign Up")
                    }
                }
            }
            authState.value.DisplayResult(
                onLoading = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                },
                onSuccess = {
                    onSuccess()
                    viewModel.resetState()
                },
                onError = {
                    onError(it)
                    viewModel.resetState()
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            HorizontalPager(
                state = pagerState,
                userScrollEnabled = false
            ) {
                if (selectedSignType.value == 0) {
                    SignInComponent(
                        isNotLoading = !isLoading,
                        onSignInButtonClick = { username, password ->
                            viewModel.signInUser(username, password)
                        }
                    )
                } else {
                    RegisterComponent(
                        isNotLoading = !isLoading,
                        onSignUpButtonClick = { fullName, username, password ->
                            viewModel.signUpUser(username, fullName, password)
                        }
                    )
                }
            }
        }

    }
}