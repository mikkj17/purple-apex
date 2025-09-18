package com.example.purpleapex.driver.domain

import kotlinx.datetime.LocalDate

data class DriverDetail(
    val code: String?,
    val dateOfBirth: LocalDate,
    val familyName: String,
    val givenName: String,
    val id: String,
    val nationality: String,
    val number: Int?,
    val url: String,
) {
    val fullName: String
        get() = "$givenName $familyName"
}
