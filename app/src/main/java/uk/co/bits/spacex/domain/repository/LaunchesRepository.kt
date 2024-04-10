package uk.co.bits.spacex.domain.repository

import uk.co.bits.spacex.domain.model.Launch

interface LaunchesRepository {
    suspend fun getLaunches(): Result<List<Launch>>
}
