package com.rmaprojects.owner.di

import com.rmaprojects.core.domain.repository.CoreAuthRepository
import com.rmaprojects.core.domain.repository.CoreBranchRepository
import com.rmaprojects.core.domain.repository.CoreLaundryRepository
import com.rmaprojects.owner.data.repository.OwnerRepositoryImpl
import com.rmaprojects.owner.domain.repository.OwnerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OwnerModules {

    @Provides
    @Singleton
    fun provideOwnerRepository(
        coreLaundryRepository: CoreLaundryRepository,
        coreAuthRepository: CoreAuthRepository,
        coreBranchRepository: CoreBranchRepository
    ): OwnerRepository {
        return OwnerRepositoryImpl(coreLaundryRepository, coreBranchRepository, coreAuthRepository)
    }

}