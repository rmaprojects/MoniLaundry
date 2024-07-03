package com.rmaprojects.core.data.repository

import com.rmaprojects.core.data.source.remote.BranchRemoteDatasource
import com.rmaprojects.core.domain.repository.CoreBranchRepository
import javax.inject.Inject

class CoreBranchRepositoryImpl @Inject constructor(
    private val branchRemoteDatasource: BranchRemoteDatasource
): CoreBranchRepository {

}