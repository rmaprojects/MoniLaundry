package com.rmaprojects.owner.presentation.screens.details.employee.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rmaprojects.core.common.Roles
import com.rmaprojects.core.presentation.components.BranchCard
import com.rmaprojects.core.presentation.components.GlobalTextField
import com.rmaprojects.owner.domain.model.BranchData
import com.rmaprojects.owner.domain.model.EmployeeData
import com.rmaprojects.owner.presentation.components.DatePicker

@Composable
fun EditEmployeeContent(
    employeeData: EmployeeData,
    modifier: Modifier = Modifier,
    onSomethingChanged: (username: String, employeeData: Roles.Employee) -> Unit,
    onBranchClicked: () -> Unit
) {

    var username by rememberSaveable {
        mutableStateOf(employeeData.username)
    }

    var employeeDetails by rememberSaveable {
        mutableStateOf(
            Roles.Employee(
                employeeData.fullName,
                employeeData.dateOfBirth,
                employeeData.livingPlace
            )
        )
    }

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        GlobalTextField(text = username ?: "", placeHolder = "Username") {
            username = it
            onSomethingChanged(username ?: "", employeeDetails)
        }
        Spacer(modifier = Modifier.height(8.dp))
        GlobalTextField(text = employeeData.fullName, placeHolder = "Nama Lengkap") {
            employeeDetails = employeeDetails.copy(fullName = it)
            onSomethingChanged(username ?: "", employeeDetails)
        }
        Spacer(modifier = Modifier.height(8.dp))
        GlobalTextField(text = employeeData.livingPlace, placeHolder = "Tempat Tinggal") {
            employeeDetails = employeeDetails.copy(livingPlace = it)
            onSomethingChanged(username ?: "", employeeDetails)
        }
        Spacer(modifier = Modifier.height(8.dp))
        DatePicker(
            label = "Tanggal Lahir",
            value = employeeData.dateOfBirth,
            onValueChange = {
                employeeDetails = employeeDetails.copy(dateOfBirth = it)
                onSomethingChanged(username ?: "", employeeDetails)
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        BranchCard(branchName = employeeData.branchName ?: "") {
            onBranchClicked()
        }
    }


}