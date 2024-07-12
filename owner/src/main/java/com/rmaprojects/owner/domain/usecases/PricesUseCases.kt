package com.rmaprojects.owner.domain.usecases

import com.rmaprojects.apirequeststate.ResponseState
import com.rmaprojects.core.domain.model.PricesData
import com.rmaprojects.owner.domain.repository.OwnerRepository
import kotlinx.coroutines.flow.Flow

class PricesUseCases(private val repository: OwnerRepository) {

    fun insertPrices(
        pricesList: List<PricesData>
    ): Flow<ResponseState<Boolean>> {
        return repository.addPrices(pricesList)
    }

    fun updatePrices(
        branchId: String,
        pricesList: List<PricesData>
    ): Flow<ResponseState<Boolean>> {
        return repository.updatePrices(branchId, pricesList)
    }

    fun removePrice(branchId: String, priceId: Int): Flow<ResponseState<Boolean>> {
        return repository.deletePrices(branchId, priceId)
    }

    fun getAllPricesFromBranch(branchId: String): Flow<ResponseState<List<PricesData>>> {
        return repository.getAllPrices(branchId)
    }

}
