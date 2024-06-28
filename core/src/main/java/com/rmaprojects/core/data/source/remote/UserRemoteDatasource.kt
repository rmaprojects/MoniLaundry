package com.rmaprojects.core.data.source.remote

import com.rmaprojects.core.common.Roles
import com.rmaprojects.core.data.source.local.LocalUserData
import com.rmaprojects.core.data.source.remote.model.BranchDto
import com.rmaprojects.core.data.source.remote.model.EmployeeDto
import com.rmaprojects.core.data.source.remote.model.OwnerDto
import com.rmaprojects.core.data.source.remote.model.UserDto
import com.rmaprojects.core.data.source.remote.tables.SupabaseTables
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.SignOutScope
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject

class UserRemoteDatasource @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val ktorClient: HttpClient
) {

    suspend fun signEmployee(
        username: String,
        email: String,
        password: String,
        employeeData: Roles.Employee
    ) {
        ktorClient.post {
            url("auth/v1/signup")
            contentType(ContentType.Application.Json)
            setBody(
                buildJsonObject {
                    put("email", email)
                    put("password", password)
                    put(
                        "data", buildJsonObject {
                            put("username", username)
                            put("full_name", employeeData.fullName)
                            put("date_of_birth", employeeData.dateOfBirth)
                            put("living_place", employeeData.livingPlace)
                        }
                    )
                }
            )
        }
    }

    suspend fun signUpOwner(
        email: String,
        password: String,
        username: String,
        ownerData: Roles.Owner
    ): String? {
        val result = supabaseClient.auth.signUpWith(Email) {
            this.email = email
            this.password = password
            data = buildJsonObject {
                put("username", username)
                put("name", ownerData.name)
            }
        }

        return result?.id
    }

    suspend fun getOwnerInfo(uuid: String): OwnerDto {
        return supabaseClient.postgrest[SupabaseTables.OWNER].select {
            filter {
                OwnerDto::id eq uuid
            }
        }.decodeSingle()
    }

    suspend fun getEmployeeInfo(uuid: String): List<EmployeeDto> {
        return supabaseClient.postgrest[SupabaseTables.EMPLOYEE].select {
            filter {
                EmployeeDto::id eq uuid
            }
        }.decodeSingle()
    }

    suspend fun logOut() {
        supabaseClient.auth.signOut(SignOutScope.LOCAL)
        LocalUserData.clear()
    }

    suspend fun signIn(
        email: String,
        password: String
    ) {
        supabaseClient.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
    }

    suspend fun getUserByUsername(username: String): UserDto {
        return supabaseClient.postgrest[SupabaseTables.USERS].select {
            filter {
                UserDto::username eq username
            }
        }.decodeSingle()
    }

}