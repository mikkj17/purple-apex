package com.example.purpleapex.grandprix.domain

interface GrandPrixRepository {
    suspend fun getGrandPrix(year: Int, round: Int): GrandPrixDetail
}
