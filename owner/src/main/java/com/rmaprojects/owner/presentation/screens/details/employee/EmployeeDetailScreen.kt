package com.rmaprojects.owner.presentation.screens.details.employee

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person4
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.generated.owner.destinations.OwnerDashboardScreenDestination
import com.rmaprojects.owner.navigation.OwnerNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Destination<OwnerNavigation>
@Composable
fun EmployeeDetailScreen(
    employeeId: String?,
    viewModel: EmployeeDetailViewModel = hiltViewModel()
) {

    val employeeDetailState = viewModel.employeeDetailState.collectAsStateWithLifecycle()

    val isEditMode by remember {
        mutableStateOf(employeeId != null)
    }



    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = if (isEditMode) "Edit Karyawan" else "Tambahkan Karyawan") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Person4,
                contentDescription = "Person Profile",
                modifier = Modifier.size(128.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))

        }
    }

}