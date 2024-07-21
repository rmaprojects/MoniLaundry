package com.rmaprojects.owner.presentation.screens.details.employee.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rmaprojects.core.common.Roles
import com.rmaprojects.core.presentation.components.BranchCard
import com.rmaprojects.core.presentation.components.GlobalAddButton
import com.rmaprojects.core.presentation.components.GlobalPasswordTextField
import com.rmaprojects.core.presentation.components.GlobalTextField
import com.rmaprojects.owner.presentation.components.DatePicker

@Composable
fun AddEmployeeContent(
    modifier: Modifier = Modifier,
    branchData: String?,
    onAddEmployeeClick: (username: String, password: String, employeeData: Roles.Employee) -> Unit,
    onAddBranchClick: () -> Unit?
) {

    var username by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var employeeData by rememberSaveable {
        mutableStateOf(Roles.Employee("", "", ""))
    }

    Column(
        modifier = modifier
    ) {
        GlobalTextField(
            modifier = Modifier.fillMaxWidth(),
            text = username,
            placeHolder = "Username"
        ) {
            username = it
        }
        Spacer(modifier = Modifier.height(8.dp))
        GlobalPasswordTextField(text = password, placeHolder = "Password") {
            password = it
        }
        Spacer(modifier = Modifier.height(8.dp))
        GlobalTextField(text = employeeData.fullName, placeHolder = "Nama Lengkap") {
            employeeData = employeeData.copy(fullName = it)
        }
        Spacer(modifier = Modifier.height(8.dp))
        GlobalTextField(text = employeeData.livingPlace, placeHolder = "Tempat Tinggal") {
            employeeData = employeeData.copy(livingPlace = it)
        }
        Spacer(modifier = Modifier.height(8.dp))
        DatePicker(
            label = "Tanggal Lahir",
            value = employeeData.dateOfBirth,
            onValueChange = { employeeData = employeeData.copy(dateOfBirth = it) }
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (branchData == null) {
            GlobalAddButton(onClick = { onAddBranchClick() }, text = "Tambahkan Cabang")
        } else {
            BranchCard(branchName = branchData) {
                onAddBranchClick()
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { onAddEmployeeClick(username, password, employeeData) },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            Text(text = "Tambahkan Karyawan")
        }
    }

}