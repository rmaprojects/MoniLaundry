package com.rmaprojects.core.data.repository

import com.rmaprojects.core.data.source.remote.LaundryDatasource
import com.rmaprojects.core.domain.repository.CoreLaundryRepository
import javax.inject.Inject

class CoreLaundryRepositoryImpl @Inject constructor(
    private val remoteDatasource: LaundryDatasource
) : CoreLaundryRepository {
}