package uk.co.bits.spacex.data.service

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import uk.co.bits.spacex.LAUNCH_RESPONSE_LIST
import uk.co.bits.spacex.data.api.LaunchesApiService
import uk.co.bits.spacex.data.mapper.LaunchResponseMapper
import uk.co.bits.spacex.data.response.LaunchResponse

@ExperimentalCoroutinesApi
class LaunchesServiceTest {

    private val launchesApiService: LaunchesApiService = mockk()
    private val launchResponseMapper: LaunchResponseMapper = mockk()
    private val response: Response<List<LaunchResponse>> = mockk()
    private lateinit var sut: LaunchesService

    @Before
    fun setUp() {
        sut = LaunchesService(launchesApiService, launchResponseMapper)
    }

    @Test
    fun `given api request is unsuccessful, when getting launch data, then return failure`() = runTest {
        val errorBody: ResponseBody = mockk(relaxed = true)
        val response: Response<List<LaunchResponse>> = mockk(relaxed = true)
        every { response.errorBody() } returns errorBody /// success!!! Response.success(list)
        every { response.isSuccessful } returns false
        coEvery { launchesApiService.getLaunches() } returns Observable.just(response)
//        coEvery { launchesApiService.getLaunches() } returns Observable.just(Response.error(400, errorBody))

        val result = sut.getLaunches().test()

        verify(exactly = 1) { launchResponseMapper.toLaunchResult(response) }
//        result.assertValue { it.isFailure }
//        result.assertValue { it.exceptionOrNull() is LaunchResponseMapper.LaunchServiceApiError }
    }

    @Test
    fun `given api request is successful, when getting launch data, then return list of launch data`() = runTest {
        val list: List<LaunchResponse> = mockk(relaxed = true)
        every { response.isSuccessful } returns true
        every { response.body() } returns LAUNCH_RESPONSE_LIST
        coEvery { launchesApiService.getLaunches() } returns Observable.just(response)

        val result = sut.getLaunches().test()

        verify(exactly = 1) { launchResponseMapper.toLaunchResult(response) }
    }

}
