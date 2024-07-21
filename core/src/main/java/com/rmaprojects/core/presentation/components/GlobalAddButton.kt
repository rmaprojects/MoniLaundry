package com.rmaprojects.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GlobalAddButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {
    Card(
        onClick = {
            onClick()
        },
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = text)
        }
    }
}