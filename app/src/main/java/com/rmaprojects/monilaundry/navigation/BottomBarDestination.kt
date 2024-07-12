package com.rmaprojects.monilaundry.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HomeWork
import androidx.compose.material.icons.filled.LocalLaundryService
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Person4
import androidx.compose.ui.graphics.vector.ImageVector
import com.ramcosta.composedestinations.generated.owner.destinations.BranchScreenDestination
import com.ramcosta.composedestinations.generated.owner.destinations.EmployeeMenuScreenDestination
import com.ramcosta.composedestinations.generated.owner.destinations.OwnerDashboardScreenDestination
import com.ramcosta.composedestinations.generated.owner.destinations.OwnerProfileScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

data class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    val label: String
)

val ownerBottomBarList = listOf(
    BottomBarDestination(
        OwnerDashboardScreenDestination,
        Icons.Default.LocalLaundryService,
        "Laundry"
    ),
    BottomBarDestination(
        BranchScreenDestination,
        Icons.Default.HomeWork,
        "Branch"
    ),
    BottomBarDestination(
        EmployeeMenuScreenDestination,
        Icons.Default.Person4,
        "Karyawan"
    ),
    BottomBarDestination(
        OwnerProfileScreenDestination,
        Icons.Default.Person,
        "Profil"
    ),
)

val employeeBottomBarList = emptyList<BottomBarDestination>()