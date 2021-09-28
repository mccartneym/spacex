package uk.co.bits.spacex.data.service

import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import uk.co.bits.spacex.LAUNCH_RESPONSE_LIST
import uk.co.bits.spacex.data.api.LaunchesApiService
import uk.co.bits.spacex.data.response.LaunchResponse

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
    fun`given api request is unsuccessful, when getting launch data, then return null`()  = runBlockingTest {
        every { response.isSuccessful } returns false
        coEvery { launchesApiService.getLaunches() } returns response

        val result = sut.getLaunches()

        assertNull(result)
    }

    @Test
    fun`given api request is successful, when getting launch data, then return list of launch data`()  = runBlockingTest {
        every { response.isSuccessful } returns true
        every { response.body() } returns LAUNCH_RESPONSE_LIST
        coEvery { launchesApiService.getLaunches() } returns response

        val result = sut.getLaunches()

        assertEquals(LAUNCH_RESPONSE_LIST, result)
    }

}
