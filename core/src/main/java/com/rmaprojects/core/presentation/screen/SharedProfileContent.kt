package com.rmaprojects.core.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Person4
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rmaprojects.core.data.source.local.LocalUserData

@Composable
fun SharedProfileScreen(
    modifier: Modifier = Modifier,
    onProfileEdit: () -> Unit,
    onLogOut:() -> Unit
) {

    var isOnEdit by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isOnEdit) {
                    FloatingActionButton(
                        onClick = {
                            isOnEdit = !isOnEdit
                        }
                    ) {
                        Icon(Icons.Default.Cancel, contentDescription = "Cancel")
                    }
                }
                FloatingActionButton(
                    onClick = {
                        if (isOnEdit) {
                            onProfileEdit()
                            isOnEdit = false
                            return@FloatingActionButton
                        }

                        isOnEdit = true
                    }
                ) {
                    Icon(
                        if (!isOnEdit) Icons.Default.Edit else Icons.Default.Done,
                        contentDescription = "Edit Profile"
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                modifier = Modifier.size(256.dp),
                imageVector = if (LocalUserData.role == "owner") Icons.Default.Person else Icons.Default.Person4,
                contentDescription = null
            )
            Spacer(Modifier.height(24.dp))
            if (isOnEdit) {
                // TODO: Implement TextField for Profile Editing
            } else {
                // TODO: Implement Text for Profile Display
            }

            Spacer(Modifier.height(32.dp))
            Button(
                onClick = onLogOut,
                modifier = Modifier.height(24.dp),
            ) {
                Icon(Icons.AutoMirrored.Filled.Logout, "Log out from your account")
                Text("Log Out")
            }
        }
    }
}