package com.example.purpleapex.standings.domain

data class Driver(
    val code: String?,
    val dateOfBirth: String,
    val familyName: String,
    val givenName: String,
    val id: String,
    val nationality: String,
    val number: Int?,
)
