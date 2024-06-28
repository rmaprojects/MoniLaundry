package com.rmaprojects.core.data.source.local

import com.chibatching.kotpref.KotprefModel

object LocalUserData: KotprefModel() {
    var uuid by nullableStringPref()
    var username by nullableStringPref()
    var email by nullableStringPref()
    var role by nullableStringPref()
    var fullName by nullableStringPref()
    var dateOfBirth by nullableStringPref()
    var livingPlace by nullableStringPref()

    override fun clear() {
        super.clear()
        uuid = null
        username = null
        email = null
        role = null
        dateOfBirth = null
        fullName = null
        livingPlace = null
    }
}