package com.rmaprojects.core.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.window.DialogProperties

@Composable
fun GlobalAlertDialog(
    modifier: Modifier = Modifier,
    title: String,
    desc: String,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable () -> Unit,
    onDismiss: () -> Unit,
    isDismissAble: Boolean = true,
    icon: ImageVector? = null
) {
    AlertDialog(
        modifier = modifier,
        icon = if (icon == null) ({}) else ({ Icon(icon, "Dialog Icon") }),
        title = {
            Text(title)
        },
        text = {
            Text(desc)
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = confirmButton,
        dismissButton = dismissButton,
        properties = DialogProperties(
            dismissOnBackPress = isDismissAble,
            dismissOnClickOutside = isDismissAble
        )
    )
}