package com.example.purpleapex.driver.domain

data class Driver(
    val id: String,
    val givenName: String,
    val familyName: String,
    val nationality: String,
    val number: Int?,
) {
    val fullName: String
        get() = "$givenName $familyName"
}
