package uk.co.bits.spacex.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import uk.co.bits.spacex.LAUNCH_RESPONSE_LIST
import uk.co.bits.spacex.domain.mapper.LaunchMapper
import uk.co.bits.spacex.data.repository.SpaceXLaunchesRepository.UnableToLoadLaunchesError
import uk.co.bits.spacex.data.response.LaunchResponse
import uk.co.bits.spacex.data.api.LaunchesService
import uk.co.bits.spacex.data.api.LaunchesService.LaunchServiceApiError

@ExperimentalCoroutinesApi
class SpaceXLaunchesRepositoryTest {

    private val launchesService: LaunchesService = mockk(relaxed = true)
    private val launchesMapper: LaunchMapper = mockk(relaxed = true)
    private lateinit var sut: SpaceXLaunchesRepository

    @Before
    fun setUp() {
        sut = SpaceXLaunchesRepository(launchesService, launchesMapper)
    }

    @Test
    fun `when launch data is loaded for the first time, then launch service loads launches`() = runTest {
        coEvery { launchesService.getLaunches() } returns Result.success(LAUNCH_RESPONSE_LIST)

        sut.getLaunches()

        coVerify(exactly = 1) { launchesService.getLaunches() }
    }

    @Test
    fun `when launch data fails to load first time, then error result is returned`() = runTest {
        val response = Result.failure<List<LaunchResponse>>(LaunchServiceApiError())
        coEvery { launchesService.getLaunches() } returns response

        val result = sut.getLaunches()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is UnableToLoadLaunchesError)
    }

    @Test
    fun `given launch data has loaded successfully, when getting launches again, then use cached data`() = runTest {
            givenLaunchDataLoadsSuccessfully()

        sut.getLaunches()

        coVerify(exactly = 1) { launchesService.getLaunches() }
    }

    @Test
    fun `given initial load data returns null, when getting launches again, then load data again`() = runTest {
        givenLaunchDataReturnsNullResponse()

        sut.getLaunches()

        coVerify(exactly = 2) { launchesService.getLaunches() }
    }

    @Test
    fun `given initial load data returns empty, when getting launches again, then load data again`() = runTest {
        givenLaunchDataReturnsEmptyResponse()

        sut.getLaunches()

        coVerify(exactly = 2) { launchesService.getLaunches() }
    }

    private suspend fun givenLaunchDataLoadsSuccessfully() {
        coEvery { launchesService.getLaunches() } returns Result.success(LAUNCH_RESPONSE_LIST)
        sut.getLaunches()
    }

    private suspend fun givenLaunchDataReturnsNullResponse() {
        coEvery { launchesService.getLaunches() } returns Result.success(null)
        sut.getLaunches()
    }

    private suspend fun givenLaunchDataReturnsEmptyResponse() {
        coEvery { launchesService.getLaunches() } returns Result.success(emptyList())
        sut.getLaunches()
    }
}
