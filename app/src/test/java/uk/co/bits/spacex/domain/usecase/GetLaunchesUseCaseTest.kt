package uk.co.bits.spacex.domain.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import uk.co.bits.spacex.LAUNCH1
import uk.co.bits.spacex.LAUNCH2
import uk.co.bits.spacex.domain.model.Launch
import uk.co.bits.spacex.domain.repository.LaunchesRepository

class GetLaunchesUseCaseTest {

    @Test
    fun `invoke should return launches from repository`() = runTest {
        val launches = listOf(LAUNCH1, LAUNCH2)
        val launchesRepository = mockk<LaunchesRepository> {
            coEvery { getLaunches() } returns Result.success(launches)
        }
        val getLaunchesUseCase = GetLaunchesUseCase(launchesRepository)

        val result = getLaunchesUseCase()

        assertThat(result).isEqualTo(Result.success(launches))
    }

    @Test
    fun `invoke should return error from repository`() = runTest {
        val exception = Exception("Network error")
        val launchesRepository = mockk<LaunchesRepository> {
            coEvery { getLaunches() } returns Result.failure(exception)
        }
        val getLaunchesUseCase = GetLaunchesUseCase(launchesRepository)

        val result = getLaunchesUseCase()

        assertThat(result).isEqualTo(Result.failure<List<Launch>>(exception))
    }
}