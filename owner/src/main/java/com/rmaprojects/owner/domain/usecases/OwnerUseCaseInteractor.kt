package com.rmaprojects.owner.domain.usecases

import com.rmaprojects.owner.domain.repository.OwnerRepository
import javax.inject.Inject

class OwnerUseCaseInteractor @Inject constructor(
    private val repository: OwnerRepository
): OwnerUseCases {
    override val branchUseCases: BranchUseCases
        get() = BranchUseCases(repository)
    override val employeeUseCases: EmployeeUseCases
        get() = EmployeeUseCases(repository)
    override val pricesUseCases: PricesUseCases
        get() = PricesUseCases(repository)
}