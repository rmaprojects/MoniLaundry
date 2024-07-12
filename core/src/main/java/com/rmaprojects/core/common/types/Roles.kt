package com.rmaprojects.core.common.types

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