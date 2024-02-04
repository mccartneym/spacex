package uk.co.bits.spacex.data.service

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import uk.co.bits.spacex.LAUNCH_RESPONSE_LIST
import uk.co.bits.spacex.data.api.LaunchesApiService
import uk.co.bits.spacex.data.response.LaunchResponse
import uk.co.bits.spacex.data.service.LaunchesService.LaunchServiceApiError

@ExperimentalCoroutinesApi
class LaunchesServiceTest {

    private val launchesApiService: LaunchesApiService = mockk()
    private val response: Response<List<LaunchResponse>> = mockk()
    private lateinit var sut: LaunchesService

    @Before
    fun setUp() {
        sut = LaunchesService(launchesApiService)
    }

    @Test
    fun `given api request is unsuccessful, when getting launch data, then return failure`() = runTest {
        every { response.isSuccessful } returns false
        coEvery { launchesApiService.getLaunches() } returns Observable.just(LAUNCH_RESPONSE_LIST)

        val result = sut.getLaunches()

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is LaunchServiceApiError)
    }

    @Test
    fun `given api request is successful, when getting launch data, then return list of launch data`() = runTest {
        every { response.isSuccessful } returns true
        every { response.body() } returns LAUNCH_RESPONSE_LIST
        coEvery { launchesApiService.getLaunches() } returns response

        val result = sut.getLaunches()

        assertEquals(Result.success(LAUNCH_RESPONSE_LIST), result)
    }

}
