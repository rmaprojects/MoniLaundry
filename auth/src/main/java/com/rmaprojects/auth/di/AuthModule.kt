package com.rmaprojects.auth.di

import com.rmaprojects.auth.data.repository.AuthRepositoryImpl
import com.rmaprojects.auth.domain.repository.AuthRepository
import com.rmaprojects.core.domain.repository.CoreAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        coreAuthRepository: CoreAuthRepository
    ): AuthRepository {
        return AuthRepositoryImpl(coreAuthRepository)
    }

}