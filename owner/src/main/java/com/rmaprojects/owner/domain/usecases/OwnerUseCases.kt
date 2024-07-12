package com.rmaprojects.owner.domain.usecases

interface OwnerUseCases {
    val countMonthIncomeUseCase: CountMonthIncomeUseCase
    val branchUseCases: BranchUseCases
    val employeeUseCases: EmployeeUseCases
    val pricesUseCases: PricesUseCases
}