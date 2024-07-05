package com.rmaprojects.core.di

import com.rmaprojects.core.BuildConfig
import com.rmaprojects.core.data.repository.CoreAuthRepositoryImpl
import com.rmaprojects.core.data.repository.CoreBranchRepositoryImpl
import com.rmaprojects.core.data.repository.CoreLaundryRepositoryImpl
import com.rmaprojects.core.data.source.remote.BranchRemoteDatasource
import com.rmaprojects.core.data.source.remote.LaundryRemoteDatasource
import com.rmaprojects.core.data.source.remote.UserRemoteDatasource
import com.rmaprojects.core.domain.repository.CoreAuthRepository
import com.rmaprojects.core.domain.repository.CoreBranchRepository
import com.rmaprojects.core.domain.repository.CoreLaundryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            BuildConfig.SUPABASE_URL,
            BuildConfig.SUPABASE_KEY
        ) {
            install(Auth)
            install(Postgrest)
            install(Realtime)
        }
    }

    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient {
        return HttpClient(OkHttp) {
            defaultRequest {
                url("${BuildConfig.SUPABASE_URL}/")
                header("apikey", BuildConfig.SUPABASE_KEY)
            }
        }
    }

    @Provides
    @Singleton
    fun provideUserRemoteDataSource(
        supabaseClient: SupabaseClient,
        ktorClient: HttpClient
    ): UserRemoteDatasource {
        return UserRemoteDatasource(supabaseClient, ktorClient)
    }

    @Provides
    @Singleton
    fun provideBranchRemoteDataSource(
        supabaseClient: SupabaseClient
    ): BranchRemoteDatasource {
        return BranchRemoteDatasource(supabaseClient)
    }

    @Provides
    @Singleton
    fun provideCoreAuthRepository(
        userRemoteDatasource: UserRemoteDatasource,
    ): CoreAuthRepository {
        return CoreAuthRepositoryImpl(userRemoteDatasource)
    }

    @Provides
    @Singleton
    fun provideCoreBranchRepository(
        branchRemoteDatasource: BranchRemoteDatasource
    ): CoreBranchRepository {
        return CoreBranchRepositoryImpl(branchRemoteDatasource)
    }

    @Provides
    @Singleton
    fun provideCoreLaundryRepository(
        laundryRemoteDatasource: LaundryRemoteDatasource
    ): CoreLaundryRepository {
        return CoreLaundryRepositoryImpl(laundryRemoteDatasource)
    }
}