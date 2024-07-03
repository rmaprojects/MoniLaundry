package com.rmaprojects.core.data.repository

import com.rmaprojects.core.common.Roles
import com.rmaprojects.core.data.source.local.LocalUserData
import com.rmaprojects.core.data.source.remote.UserRemoteDatasource
import com.rmaprojects.core.domain.repository.CoreAuthRepository
import io.ktor.http.HttpStatusCode
import javax.inject.Inject
import kotlin.random.Random

class CoreAuthRepositoryImpl @Inject constructor(
    private val userRemoteDatasource: UserRemoteDatasource,
) : CoreAuthRepository {

    override suspend fun signIn(username: String, password: String): Result<Boolean> {
        val isUserExists = userRemoteDatasource.isUserExitsInDatabase(username)
        if (isUserExists) {
            val user = userRemoteDatasource.getUserByUsername(username)
            if (user.role == "owner") {
                userRemoteDatasource.getOwnerLoginInfo(user.id).let {
                    userRemoteDatasource.signIn(it.email, password)
                    LocalUserData.apply {
                        this.role = it.role
                        this.email = it.email
                        this.username = it.username
                        this.uuid = it.id
                        this.fullName = it.ownerDetails.name
                    }
                }
            } else {
                userRemoteDatasource.getEmployeeLoginInfo(user.id).let {
                    userRemoteDatasource.signIn(it.email, password)
                    LocalUserData.apply {
                        this.role = it.role
                        this.email = it.email
                        this.username = it.username
                        this.uuid = it.id
                        this.fullName = it.employeeDetails.fullName
                        this.dateOfBirth = it.employeeDetails.dateOfBirth
                        this.livingPlace = it.employeeDetails.livingPlace
                    }
                }
            }

            return Result.success(true)
        } else {
            return Result.failure(Exception("User not found"))
        }

    }

    override suspend fun signUpOwner(
        username: String,
        password: String,
        ownerData: Roles.Owner
    ): Result<Boolean> {
        val isUserExists = userRemoteDatasource.isUserExitsInDatabase(username)
        if (isUserExists) {
            return Result.failure(Exception("Pengguna Sudah terdaftar"))
        } else {
            userRemoteDatasource.signUpOwner(generateRandomEmails(), password, username, ownerData)
            val uuid = userRemoteDatasource.retrieveUserInfo()?.id
            if (uuid != null) {
                val owner = userRemoteDatasource.getOwnerLoginInfo(uuid)
                LocalUserData.apply {
                    this.uuid = owner.id
                    this.email = owner.email
                    this.role = owner.role
                    this.fullName = owner.ownerDetails.name
                    this.username = owner.username
                }
                return Result.success(true)
            } else {
                return Result.failure(Exception("Failed to create user"))
            }
        }
    }

    override suspend fun signEmployee(
        username: String,
        password: String,
        employee: Roles.Employee
    ): Result<Boolean> {
        val status = userRemoteDatasource.signEmployee(username, generateRandomEmails(), password, employee)
        return Result.success(status == HttpStatusCode.OK)
    }

    override suspend fun logOut() {
        userRemoteDatasource.logOut()
    }


    private fun generateRandomEmails(): String {
        val chars = "abcdefghijklmnopqrstuvwxyz0123456789"
        val domains = listOf("gmail.com", "mail.com", "moni.com", "domain.com")

        fun getRandomString(length: Int): String {
            return (1..length)
                .map { chars.random() }
                .joinToString("")
        }

        val usernameLength = Random.nextInt(5, 20)
        val username = getRandomString(usernameLength)

        val domain = domains.random()

        return "$username@$domain"
    }
}