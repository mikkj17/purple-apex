package com.example.purpleapex.grandprix.data.repository

import com.example.purpleapex.grandprix.domain.GrandPrixClient
import com.example.purpleapex.grandprix.domain.GrandPrixDetail
import com.example.purpleapex.grandprix.domain.GrandPrixRepository

class DefaultGrandPrixRepository(
    private val grandPrixClient: GrandPrixClient,
) : GrandPrixRepository {
    override suspend fun getGrandPrix(year: Int, round: Int): GrandPrixDetail {
        return grandPrixClient.getGrandPrix(year, round)
    }
}
