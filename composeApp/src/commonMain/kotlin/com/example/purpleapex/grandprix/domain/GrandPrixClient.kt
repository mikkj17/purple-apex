package com.example.purpleapex.grandprix.domain

interface GrandPrixClient {
    suspend fun getGrandPrix(year: Int, round: Int): GrandPrixDetail
}
