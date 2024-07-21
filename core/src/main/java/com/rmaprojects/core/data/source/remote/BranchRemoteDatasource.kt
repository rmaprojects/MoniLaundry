package com.rmaprojects.core.data.source.remote

import com.rmaprojects.core.data.source.local.LocalUserData
import com.rmaprojects.core.data.source.remote.model.BranchDto
import com.rmaprojects.core.data.source.remote.model.EmployeeDetailsDto
import com.rmaprojects.core.data.source.remote.model.PricesDto
import com.rmaprojects.core.data.source.remote.tables.SupabaseTables
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

class BranchRemoteDatasource @Inject constructor(
    private val supabaseClient: SupabaseClient
) {
    suspend fun getAllStoreBranch(): List<BranchDto> {
        return supabaseClient.postgrest[SupabaseTables.BRANCH]
            .select()
            .decodeList()
    }

    suspend fun getAllBranchWithOrderHistory(
        orderRangeFrom: String?,
        orderRangeTo: String?
    ): List<BranchDto> {
        return supabaseClient.postgrest[SupabaseTables.BRANCH]
            .select(
                Columns.raw(
                    """
                        *, 
                        tbl_laundry_history(*, tbl_history_details(*))
                    """.trimIndent()
                ),
                request = if (orderRangeTo.isNullOrEmpty() || orderRangeFrom.isNullOrEmpty()) ({}) else ({
                    filter {
                        and {
                            gte("tbl_laundry_history.created_at", orderRangeFrom)
                            lte("tbl_laundry_history.created_at", orderRangeTo)
                        }
                    }
                })
            ).decodeList()
    }

    suspend fun getStoreBranchInfo(branchId: String): BranchDto {
        return supabaseClient.postgrest[SupabaseTables.BRANCH].select(
            Columns.raw(
                """
                    *, tbl_employee(*), tbl_prices(*)
                """.trimIndent()
            )
        ) {
            filter {
                BranchDto::id eq branchId
            }
        }.decodeSingle()
    }

    suspend fun getEmployeeList(
        branchId: String? = null
    ): List<EmployeeDetailsDto> {
        return supabaseClient.postgrest[SupabaseTables.EMPLOYEE]
            .select(
                request =
                if (branchId == null) ({
                    filter {
                        EmployeeDetailsDto::ownerBranchId eq LocalUserData.uuid
                    }
                })
                else ({
                    filter {
                        and {
                            EmployeeDetailsDto::branchId eq branchId
                            EmployeeDetailsDto::ownerBranchId eq LocalUserData.uuid
                        }
                    }
                })
            )
            .decodeList<EmployeeDetailsDto>()
    }

    suspend fun getEmployeeDetail(
        employeeId: String
    ): EmployeeDetailsDto {
        return supabaseClient.postgrest[SupabaseTables.EMPLOYEE]
            .select(
                Columns.raw(
                    """
                        *, tbl_branch(*)
                    """.trimIndent()
                )
            ) {
                filter {
                    EmployeeDetailsDto::id eq employeeId
                }
            }.decodeSingle()
    }

    suspend fun insertNewBranch(
        longitude: Float = 0f,
        latitude: Float = 0f,
        imageUrl: String? = null,
        name: String,
        ownerId: String? = LocalUserData.uuid
    ): BranchDto {
        if (ownerId == null) {
            throw Exception("You're signed out, please login")
        }

        return supabaseClient.postgrest[SupabaseTables.BRANCH].insert(
            BranchDto(
                longitude = longitude,
                latitude = latitude,
                imageUrl = imageUrl ?: "",
                ownerId = ownerId,
                name = name
            )
        ).decodeSingle()
    }

    suspend fun updateBranch(
        branchId: String,
        newName: String,
        newLongitude: Float,
        newLatitude: Float,
        newImageUrl: String? = null
    ) {
        val userId = LocalUserData.uuid ?: throw Exception("You're signed out, please login")
        supabaseClient.postgrest[SupabaseTables.BRANCH].update(
            BranchDto(
                longitude = newLongitude,
                latitude = newLatitude,
                imageUrl = newImageUrl,
                ownerId = userId,
                name = newName
            )
        ) {
            filter {
                BranchDto::id eq branchId
            }
        }
    }

    suspend fun deleteBranch(
        branchId: String
    ) {
        supabaseClient.postgrest[SupabaseTables.BRANCH].delete {
            filter {
                BranchDto::id eq branchId
            }
        }
    }

    suspend fun editEmployee(
        employeeId: String,
        newBranchId: String
    ) {
        supabaseClient.postgrest[SupabaseTables.EMPLOYEE].update(
            {
                EmployeeDetailsDto::branchId setTo newBranchId
            }
        ) {
            filter {
                EmployeeDetailsDto::id eq employeeId
            }
        }
    }

    suspend fun insertNewPrices(
        listPrices: List<PricesDto>
    ) {
        supabaseClient.postgrest[SupabaseTables.PRICES].insert(listPrices)
    }

    suspend fun updateNewPrices(
        branchId: String,
        listPrices: List<PricesDto>
    ) {
        supabaseClient.postgrest[SupabaseTables.PRICES].update(
            listPrices
        ) {
            filter {
                PricesDto::branchId eq branchId
            }
        }
    }

    suspend fun deletePrices(
        branchId: String,
        priceId: Int
    ) {
        supabaseClient.postgrest[SupabaseTables.PRICES].delete {
            filter {
                and {
                    PricesDto::branchId eq branchId
                    PricesDto::id eq priceId
                }
            }
        }
    }

    suspend fun getAllPrices(
        branchId: String
    ): List<PricesDto> {
        return supabaseClient.postgrest[SupabaseTables.PRICES].select {
            filter {
                PricesDto::branchId eq branchId
            }
        }.decodeList()
    }
}