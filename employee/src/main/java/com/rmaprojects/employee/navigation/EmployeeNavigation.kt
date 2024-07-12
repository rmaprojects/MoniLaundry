package com.rmaprojects.employee.navigation

import com.ramcosta.composedestinations.annotation.ExternalModuleGraph
import com.ramcosta.composedestinations.annotation.NavGraph

@NavGraph<ExternalModuleGraph>(
    route = "employee"
)
annotation class EmployeeNavigation
