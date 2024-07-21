package com.rmaprojects.owner.presentation.screens.details.branch.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rmaprojects.core.domain.model.PricesData
import com.rmaprojects.core.presentation.components.GlobalAddButton
import com.rmaprojects.core.presentation.components.PricesCard
import com.rmaprojects.owner.domain.model.BranchData


@Composable
fun AddBranchContent(
    modifier: Modifier = Modifier,
    branchDetailInfo: BranchData,
    onAddLocationClick: () -> Unit,
    onSelectBranchClick: () -> Unit,
    onSubmitButtonClick: () -> Unit,
    onEditProduct: (PricesData) -> Unit,
    onDeleteProduct: (PricesData) -> Unit
) {

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Lokasi Cabang")
        Spacer(modifier = Modifier.height(4.dp))
        GlobalAddButton(onClick = onAddLocationClick, text = "Tambahkan Lokasi")
        Spacer(modifier = Modifier.height(24.dp))
        if (!branchDetailInfo.productList.isNullOrEmpty()) {
            LazyColumn {
                items(branchDetailInfo.productList) {
                    PricesCard(
                        productName = it.itemName,
                        laundryType = it.laundryType,
                        price = it.price,
                        onClick = { },
                        onEditClick = { onEditProduct(it) },
                        onDeleteClick = { onDeleteProduct(it) }
                    )
                }
            }
        } else {
            Text(text = "Tambahkan Harga")
            Spacer(modifier = Modifier.height(4.dp))
            GlobalAddButton(onClick = onSelectBranchClick, text = "Tambahkan Harga")
        }
        Button(onClick = onSubmitButtonClick) {
            Text(text = "Submit")
        }
    }
}