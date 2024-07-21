package com.rmaprojects.core.presentation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun GlobalTextField(
    modifier: Modifier = Modifier,
    text: String,
    placeHolder: String,
    onInput: (String) -> Unit,
) {
    TextField(
        text,
        modifier = modifier,
        onValueChange = onInput,
        placeholder = { Text(placeHolder) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
    )
}

@Composable
fun GlobalPasswordTextField(
    modifier: Modifier = Modifier,
    text: String,
    placeHolder: String,
    onInput: (String) -> Unit
) {

    var isPasswordShown by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        text,
        modifier = modifier,
        onValueChange = onInput,
        placeholder = { Text(placeHolder) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        visualTransformation = if (isPasswordShown) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = {
                isPasswordShown = !isPasswordShown
            }) {
                Icon(Icons.Default.Visibility, contentDescription = "Show Password")
            }

        }
    )
}