package com.rmaprojects.core.data.source.remote

import com.rmaprojects.core.data.source.remote.model.BranchDto
import com.rmaprojects.core.data.source.remote.model.EmployeeDetails
import com.rmaprojects.core.data.source.remote.tables.SupabaseTables
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import javax.inject.Inject

class BranchRemoteDatasource @Inject constructor(
    private val supabaseClient: SupabaseClient
) {
    suspend fun getAllStoreBranch(): List<BranchDto> {
        return supabaseClient.postgrest[SupabaseTables.BRANCH]
            .select()
            .decodeList()
    }

    suspend fun getStoreBranchInfo(branchId: String): BranchDto {
        return supabaseClient.postgrest[SupabaseTables.BRANCH].select {
            filter {
                BranchDto::id eq branchId
            }
        }.decodeSingle()
    }

    suspend fun getEmployeeList(
        branchId: String? = null
    ): List<EmployeeDetails> {
        return supabaseClient.postgrest[SupabaseTables.EMPLOYEE]
            .select(
                request =
                if (branchId == null) ({})
                else ({
                    filter {
                        EmployeeDetails::branchId eq branchId
                    }
                })
            )
            .decodeList<EmployeeDetails>()
    }
}