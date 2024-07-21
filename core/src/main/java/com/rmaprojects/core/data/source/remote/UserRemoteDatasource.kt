package com.rmaprojects.core.data.source.remote

import com.rmaprojects.core.common.Roles
import com.rmaprojects.core.data.source.local.LocalUserData
import com.rmaprojects.core.data.source.remote.model.EmployeeDetailsDto
import com.rmaprojects.core.data.source.remote.model.EmployeeDto
import com.rmaprojects.core.data.source.remote.model.OwnerDetailsDto
import com.rmaprojects.core.data.source.remote.model.OwnerDto
import com.rmaprojects.core.data.source.remote.model.UserDto
import com.rmaprojects.core.data.source.remote.tables.SupabaseTables
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseInternal
import io.github.jan.supabase.gotrue.SignOutScope
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Count
import io.github.jan.supabase.safeBody
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
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
    ): UserInfo {
        val result = ktorClient.post {
            url("auth/v1/signup")
            contentType(ContentType.Application.Json)
            setBody(
                buildJsonObject {
                    put("email", email)
                    put("password", password)
                    put(
                        "data", buildJsonObject {
                            put("username", username)
                            put("role", "employee")
                            put("full_name", employeeData.fullName)
                            put("date_of_birth", employeeData.dateOfBirth)
                            put("living_place", employeeData.livingPlace)
                        }
                    )
                }
            )
        }

        return result.body<UserInfo>()
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
            this.data = buildJsonObject {
                put("username", username)
                put("name", ownerData.name)
                put("role", "owner")
            }
        }

        return result?.id
    }

    suspend fun getOwnerLoginInfo(uuid: String): OwnerDto {
        return supabaseClient.postgrest[SupabaseTables.USERS].select(
            columns = Columns.raw(
                """
                    *,
                    tbl_owner(*)
                """.trimIndent()
            )
        ) {
            filter {
                OwnerDto::id eq uuid
            }
        }.decodeSingle()
    }

    suspend fun getEmployeeLoginInfo(uuid: String): EmployeeDto {
        return supabaseClient.postgrest[SupabaseTables.USERS].select(
            columns = Columns.raw(
                """
                    *,
                    tbl_employee(*)
                """.trimIndent()
            )
        ) {
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
    ): UserInfo? {
        supabaseClient.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }

        return retrieveUserInfo()
    }

    fun retrieveUserInfo(): UserInfo? {
        return supabaseClient.auth.currentUserOrNull()
    }

    suspend fun getUserByUsername(username: String): UserDto {
        return supabaseClient.postgrest[SupabaseTables.USERS].select {
            filter {
                UserDto::username eq username
            }
        }.decodeSingle()
    }

    suspend fun isUserExitsInDatabase(username: String): Boolean {
        return supabaseClient.postgrest[SupabaseTables.USERS].select {
            filter {
                UserDto::username eq username
            }
            count(Count.EXACT)
        }.countOrNull() == 1L
    }

    suspend fun deleteAccount(accountId: String) {
        val employee = getEmployeeLoginInfo(accountId)
        if (employee.id != null) supabaseClient.auth.admin.deleteUser(employee.id)
    }

    suspend fun deleteAccount() {
        supabaseClient.auth.signOut(SignOutScope.LOCAL)
        supabaseClient.auth.admin.deleteUser(LocalUserData.uuid ?: "")
        LocalUserData.clear()
    }

    suspend fun updatePassword(employeeId: String? = null, newPassword: String) {
        val isUpdatingCurrentUser = employeeId == null
        supabaseClient.auth.updateUser(
            isUpdatingCurrentUser
        ) {
            this.password = newPassword
        }
    }

    suspend fun updateOwnerInfo(
        newUsername: String,
        ownerData: Roles.Owner,
        ownerId: String? = LocalUserData.uuid,
    ) {
        supabaseClient.postgrest[SupabaseTables.USERS]
            .update(
                {
                    UserDto::username setTo newUsername
                }
            ) {
                filter {
                    UserDto::id eq ownerId
                }
            }
        supabaseClient.postgrest[SupabaseTables.EMPLOYEE]
            .update(
                OwnerDetailsDto(
                    ownerData.name
                )
            ) {
                filter {
                    EmployeeDto::id eq ownerId
                }
            }
    }

    suspend fun updateEmployeeInfo(
        employeeId: String,
        newUsername: String,
        newEmployeeData: Roles.Employee
    ) {
        supabaseClient.postgrest[SupabaseTables.USERS]
            .update(
                {
                    UserDto::username setTo newUsername
                }
            ) {
                filter {
                    UserDto::id eq employeeId
                }
            }
        supabaseClient.postgrest[SupabaseTables.EMPLOYEE]
            .update(
                EmployeeDetailsDto(
                    newEmployeeData.fullName,
                    newEmployeeData.dateOfBirth,
                    newEmployeeData.livingPlace
                )
            ) {
                filter {
                    EmployeeDto::id eq employeeId
                }
            }
    }
}