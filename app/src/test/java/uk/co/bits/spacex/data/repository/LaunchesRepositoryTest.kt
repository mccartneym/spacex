package uk.co.bits.spacex.data.repository

import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import uk.co.bits.spacex.LAUNCH_RESPONSE_LIST
import uk.co.bits.spacex.data.mapper.LaunchMapper
import uk.co.bits.spacex.data.repository.LaunchesRepository.UnableToLoadLaunchesError
import uk.co.bits.spacex.data.response.LaunchResponse
import uk.co.bits.spacex.data.service.LaunchesService
import uk.co.bits.spacex.data.service.LaunchesService.LaunchServiceApiError

@ExperimentalCoroutinesApi
class LaunchesRepositoryTest {

    private val launchesService: LaunchesService = mockk(relaxed = true)
    private val launchesMapper: LaunchMapper = mockk(relaxed = true)
    private lateinit var sut: LaunchesRepository

    @Before
    fun setUp() {
        sut = LaunchesRepository(launchesService, launchesMapper)
    }

    @Test
    fun `when launch data is loaded for the first time, then launch service loads launches`() = runBlockingTest {
        coEvery { launchesService.getLaunches() } returns Result.success(LAUNCH_RESPONSE_LIST)

        sut.getLaunches()

        coVerify(exactly = 1) { launchesService.getLaunches() }
    }

    @Test
    fun `when launch data fails to load first time, then error result is returned`() = runBlockingTest {
        val response = Result.failure<List<LaunchResponse>>(LaunchServiceApiError())
        coEvery { launchesService.getLaunches() } returns response

        val result = sut.getLaunches()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is UnableToLoadLaunchesError)
    }

    @Test
    fun `given launch data has loaded successfully, when getting launches again, then cached data is provided instead of reloading`() =
        runBlockingTest {
            givenLaunchDataLoadsSuccessfully()

            sut.getLaunches()

            coVerify(exactly = 1) { launchesService.getLaunches() }
        }

    @Test
    fun `given initial attempt to load data returns null, when getting launches again, then subsequent attempt to load data is made`() =
        runBlockingTest {
            givenLaunchDataReturnsNullResponse()

            sut.getLaunches()

            coVerify(exactly = 2) { launchesService.getLaunches() }
        }

    @Test
    fun `given initial attempt to load data returns empty, when getting launches again, then subsequent attempt to load data is made`() =
        runBlockingTest {
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
