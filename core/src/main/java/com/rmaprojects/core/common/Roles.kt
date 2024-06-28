package com.rmaprojects.core.common

sealed class Roles {
    data class Employee(
        val fullName: String,
        val dateOfBirth: String,
        val livingPlace: String
    ): Roles()
    data class Owner(
        val name: String
    ): Roles()
}