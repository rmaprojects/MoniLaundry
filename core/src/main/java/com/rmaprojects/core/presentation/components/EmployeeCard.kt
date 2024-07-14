package com.rmaprojects.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.rmaprojects.core.R

@Composable
fun EmployeeCard(
    modifier: Modifier = Modifier,
    employeeImageUrl: String = "",
    employeeName: String,
    branchName: String?,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AsyncImage(
                model = if (employeeImageUrl == "") painterResource(R.drawable.store_logo) else employeeImageUrl,
                contentDescription = null
            )
            Column {
                Text(
                    employeeName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.W500
                )
                if (branchName != null) Text(branchName, style = MaterialTheme.typography.bodySmall)
            }

        }

    }
}