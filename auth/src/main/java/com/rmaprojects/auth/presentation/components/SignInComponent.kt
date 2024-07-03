package com.rmaprojects.auth.presentation.components

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun SignInComponent(
    modifier: Modifier = Modifier,
    onSignInButtonClick: (username: String, password: String) -> Unit,
    isNotLoading: Boolean
) {

    var usernameText by rememberSaveable {
        mutableStateOf("")
    }
    var passwordText by rememberSaveable {
        mutableStateOf("")
    }
    var isPasswordShown by rememberSaveable {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    Column(modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = usernameText,
            onValueChange = { usernameText = it },
            placeholder = { Text(text = "Username") },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = passwordText,
            onValueChange = { passwordText = it },
            placeholder = { Text(text = "Password") },
            visualTransformation = if (!isPasswordShown) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                IconButton(onClick = { isPasswordShown = !isPasswordShown }) {
                    Icon(
                        imageVector = if (isPasswordShown) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle Password"
                    )
                }
            },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = isNotLoading,
                onClick = {
                    if (usernameText.isEmpty() || passwordText.isEmpty()) {
                        Toast.makeText(context, "Mohon isi terlebih dahulu", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    onSignInButtonClick(usernameText, passwordText)
                }
            ) {
                Text(text = "Sign In")
            }
        }
    }

}