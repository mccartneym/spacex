package uk.co.bits.spacex.data.repository

import uk.co.bits.spacex.data.model.Launch

interface LaunchesRepository {
    suspend fun getLaunches(): Result<List<Launch>>
}