package com.rmaprojects.core.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SharedErrorScreen(
    modifier: Modifier = Modifier,
    onRetryAction: () -> Unit,
    exceptionMessage: String
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(exceptionMessage, style = MaterialTheme.typography.displayMedium)
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = onRetryAction
        ) {
            Text("Retry?")
        }

    }
}