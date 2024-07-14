package com.rmaprojects.owner.domain.usecases

import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.core.common.Roles
import com.rmaprojects.owner.domain.model.EmployeeData
import com.rmaprojects.owner.domain.repository.OwnerRepository
import kotlinx.coroutines.flow.Flow

class EmployeeUseCases(val repository: OwnerRepository) {

    fun assignEmployeeBranch(employeeId: String, branchId: String): Flow<ResponseState<Boolean>> {
        return repository.editEmployeeBranch(employeeId, branchId)
    }

    fun addEmployee(
        username: String,
        password: String,
        employee: Roles.Employee
    ): Flow<ResponseState<Boolean>> {
        return repository.addEmployee(username, password, employee)
    }

    fun editEmployee(
        username: String,
        password: String,
        employee: Roles.Employee
    ): Flow<ResponseState<Boolean>> {
        return repository.editEmployee(username, password, employee)
    }

    fun deleteEmployee(
        employeeId: String
    ): Flow<ResponseState<Boolean>> {
        return repository.deleteEmployee(employeeId)
    }



    fun getEmployeeInfo(
        employeeId: String
    ): Flow<ResponseState<EmployeeData>> {
        return repository.getEmployeeInfo(employeeId)
    }

    fun getAllEmployee(): Flow<ResponseState<List<EmployeeData>>> {
        return repository.getAllEmployee()
    }

}