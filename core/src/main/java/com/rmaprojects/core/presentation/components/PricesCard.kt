package com.rmaprojects.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rmaprojects.core.R
import com.rmaprojects.core.common.LaundryStatus
import com.rmaprojects.core.common.LaundryType

@Composable
fun PricesCard(
    modifier: Modifier = Modifier,
    productName: String,
    laundryType: LaundryType,
    price: Int,
    onClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    laundryStatus: LaundryStatus? = null
) {

    OutlinedCard(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                productName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text("Tipe: ${laundryType.typeName}", style = MaterialTheme.typography.bodySmall)
            Spacer(Modifier.height(12.dp))
            Text("Harga: 1${if (laundryType == LaundryType.SINGLE) "pcs" else "kg"} x $price")
            Spacer(Modifier.height(2.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onEditClick) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Product")
                }
                Spacer(Modifier.width(8.dp))
                IconButton(onClick = onDeleteClick) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Product")
                }
            }
            if (laundryStatus != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Status: ", style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.width(2.dp))
                    Text(
                        laundryStatus.name,
                        style = MaterialTheme.typography.bodyLarge,
                        color = when (laundryStatus) {
                            LaundryStatus.CANCELED -> colorResource(R.color.status_canceled)
                            LaundryStatus.PROCESS -> colorResource(R.color.status_process)
                            LaundryStatus.FINISHED -> colorResource(R.color.status_finished)
                        }
                    )
                }
            }
        }
    }

}