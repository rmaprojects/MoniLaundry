package com.rmaprojects.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rmaprojects.core.common.LaundryType

@Composable
fun PriceCard(
    productName: String,
    productType: LaundryType,
    pricePerQty: Int,
    onDelete:() -> Unit,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text("Nama: $productName")
            Spacer(Modifier.height(2.dp))
            Text("Tipe: ${productType.typeName}")
            Spacer(Modifier.height(8.dp))
            Text("Harga: $pricePerQty/1${if (productType == LaundryType.SINGLE) "pcs" else "kg"}")
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = onEdit
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Edit Price"
                    )
                }
                IconButton(
                    onClick = onDelete
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete Price",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}