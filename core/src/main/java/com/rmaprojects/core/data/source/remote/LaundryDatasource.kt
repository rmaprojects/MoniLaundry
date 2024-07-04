package com.rmaprojects.core.data.source.remote

import com.rmaprojects.core.data.source.remote.model.LaundryHistoryDetailsDto
import com.rmaprojects.core.data.source.remote.model.LaundryHistoryDto
import com.rmaprojects.core.data.source.remote.tables.SupabaseTables
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import javax.inject.Inject

class LaundryDatasource @Inject constructor(
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

}