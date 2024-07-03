package com.rmaprojects.auth.presentation.screen.chooser

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Person4
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.generated.destinations.AuthScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rmaprojects.auth.navigation.AuthNavigation
import com.rmaprojects.auth.presentation.components.RoleCard

@Destination<AuthNavigation>(start = true)
@Composable
fun AuthChooserScreen(
    navigator: DestinationsNavigator
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Selamat datang, silahkan pilih tipe akun anda",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.W500,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
        item {
            RoleCard(icon = Icons.Default.Person4, text = "Karyawan") {
                navigator.navigate(AuthScreenDestination(true))
            }
        }
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
        item {
            RoleCard(icon = Icons.Default.Person, text = "Owner") {
                navigator.navigate(AuthScreenDestination(false))
            }
        }

    }
}