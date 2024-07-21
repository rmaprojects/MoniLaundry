package com.rmaprojects.owner.presentation.screens.details.branch.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.rmaprojects.core.R
import com.rmaprojects.core.presentation.components.EmployeeCard
import com.rmaprojects.core.presentation.components.GlobalTextField
import com.rmaprojects.core.presentation.components.PricesCard
import com.rmaprojects.owner.domain.model.BranchData

@Composable
fun EditBranchContent(
    branchDetailData: BranchData,
    onBranchDataChanged: (BranchData) -> Unit,
    modifier: Modifier = Modifier
) {

    val editAbleBranchDetailData = remember {
        mutableStateOf(branchDetailData)
    }

    var isShowingAllEmployee by remember {
        mutableStateOf(false)
    }

    var isShowingAllProducts by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        AsyncImage(
            model =
            if (editAbleBranchDetailData.value.imageUrl.isNullOrEmpty()) painterResource(id = R.drawable.store_logo)
            else editAbleBranchDetailData.value.imageUrl,
            contentDescription = "Branch Image"
        )
        Spacer(modifier = Modifier.height(12.dp))
        GlobalTextField(text = editAbleBranchDetailData.value.name, placeHolder = "Branch Name") {
            editAbleBranchDetailData.value = editAbleBranchDetailData.value.copy(name = it)
            onBranchDataChanged(editAbleBranchDetailData.value)
        }
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "List karyawan")
            Button(onClick = { isShowingAllEmployee = !isShowingAllEmployee }) {
                Text("Tampilkan semua")
            }
        }
        LazyColumn {
            if (editAbleBranchDetailData.value.employeeList?.isEmpty() == true) {
                item {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(text = "Karyawan belum ditambahkan")
                    }
                }
            } else {
                items(
                    if (!isShowingAllEmployee) editAbleBranchDetailData.value.employeeList!!.take(3)
                    else editAbleBranchDetailData.value.employeeList!!
                ) { employeeData ->
                    EmployeeCard(
                        employeeName = employeeData.fullName,
                        branchName = employeeData.branchName,
                        onClick = {}
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "List harga barang")
            Button(onClick = { isShowingAllProducts = !isShowingAllProducts }) {
                Text("Tampilkan semua")
            }
        }
        LazyColumn {
            if (editAbleBranchDetailData.value.productList?.isEmpty() == true) {
                item {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(text = "Belum ada produk yang ditambahkan")
                    }
                }
            } else {
                items(
                    if (!isShowingAllProducts) editAbleBranchDetailData.value.productList!!.take(3)
                    else editAbleBranchDetailData.value.productList!!
                ) { prices ->
                    PricesCard(
                        productName = prices.itemName,
                        laundryType = prices.laundryType,
                        price = prices.price,
                        onClick = {},
                        onEditClick = {},
                        onDeleteClick = {}
                    )
                }
            }
        }
    }
}