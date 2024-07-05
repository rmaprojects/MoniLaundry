package com.rmaprojects.core.data.source.remote

import com.rmaprojects.core.common.LaundryStatus
import com.rmaprojects.core.data.source.remote.model.LaundryHistoryDetailsDto
import com.rmaprojects.core.data.source.remote.model.LaundryHistoryDto
import com.rmaprojects.core.data.source.remote.tables.SupabaseTables
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Count
import javax.inject.Inject

class LaundryRemoteDatasource @Inject constructor(
    private val supabaseClient: SupabaseClient
) {

    suspend fun getAllLaundryHistory(): List<LaundryHistoryDto> {
        return supabaseClient.postgrest[SupabaseTables.LAUNDRY_HISTORY]
            .select()
            .decodeList()
    }

    suspend fun getLaundryHistory(
        orderId: String
    ): LaundryHistoryDto {
        return supabaseClient.postgrest[SupabaseTables.LAUNDRY_HISTORY]
            .select(
                columns = Columns.raw(
                    """
                        *, tbl_history_details(*, tbl_prices(items, type, price))
                    """.trimIndent()
                )
            ) {
                filter {
                    LaundryHistoryDto::orderId eq orderId
                }
            }.decodeSingle()
    }

    suspend fun insertLaundryInput(
        employeeId: String,
        branchId: String,
        status: LaundryStatus = LaundryStatus.PROCESS,
        customerName: String,
        customerAddress: String,
        orderList: List<LaundryHistoryDetailsDto>
    ) {
        val orderId = supabaseClient.postgrest[SupabaseTables.LAUNDRY_HISTORY]
            .insert(
                LaundryHistoryDto(
                    customerName, customerAddress, status.statusText, branchId, employeeId
                )
            ).decodeSingle<LaundryHistoryDto>()
            .orderId
        if (orderId == null) {
            throw Exception("Gagal menambahkan pesanan laundry")
        }
        val mappedList = orderList.map {
            it.copy(orderId = orderId)
        }
        supabaseClient.postgrest[SupabaseTables.HISTORY_DETAIL].insert(
            mappedList
        )
    }

    suspend fun changeLaundryStatus(
        orderId: String,
        newStatus: LaundryStatus
    ) {

        supabaseClient.postgrest[SupabaseTables.LAUNDRY_HISTORY]
            .update(
                {
                    LaundryHistoryDto::status setTo newStatus.statusText
                }
            ) {
                filter {
                    LaundryHistoryDto::orderId eq orderId
                }
            }

    }

    suspend fun checkIfOrderIdExists(
        orderId: String
    ): Boolean {
        return supabaseClient.postgrest[SupabaseTables.LAUNDRY_HISTORY]
            .select {
                count(Count.EXACT)
                filter {
                    LaundryHistoryDto::orderId eq orderId
                }
            }.countOrNull() == 1L
    }

    suspend fun deleteLaundryOrderHistory(
        orderId: String
    ) {
        supabaseClient.postgrest[SupabaseTables.LAUNDRY_HISTORY]
            .delete {
                filter {
                    LaundryHistoryDto::orderId eq orderId
                }
            }
    }
}