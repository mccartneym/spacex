package uk.co.bits.spacex.data.mapper

import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import uk.co.bits.spacex.data.response.LaunchResponse
import uk.co.bits.spacex.data.response.Links
import uk.co.bits.spacex.data.response.Patch

class LaunchMapperTest {

    private val dateMapper: DateMapper = mockk()
    private lateinit var sut: LaunchMapper

    @Before
    fun setUp() {
        sut = LaunchMapper(dateMapper)
        every { dateMapper.parseUnixDate(DATE) } returns PARSED_DATE
    }

    @Test
    fun `given LaunchResponse object, when parsing to model, then URL is parsed successfully`() {
        val result = sut.toLaunch(LAUNCH_RESPONSE)

        assertEquals(URL, result.smallImageUrl)
    }

    @Test
    fun `given LaunchResponse object, when parsing to model, then mission success state is parsed successfully`() {
        val result = sut.toLaunch(LAUNCH_RESPONSE)

        assertEquals(MISSION_SUCCESS, result.success)
    }

    @Test
    fun `given LaunchResponse object, when parsing to model, then launch name is parsed successfully`() {
        val result = sut.toLaunch(LAUNCH_RESPONSE)

        assertEquals(NAME, result.name)
    }

    @Test
    fun `given LaunchResponse object, when parsing to model, then date is parsed successfully`() {
        val result = sut.toLaunch(LAUNCH_RESPONSE)

        assertEquals(PARSED_DATE, result.date)
    }

    companion object {
        private const val URL = "smallImageUrl"
        private const val MISSION_SUCCESS = true
        private const val NAME = "launch name"
        private const val DATE = 1143239400L
        private const val PARSED_DATE = "04-06-2010"

        private val PATCH = Patch(small = URL)
        private val LINKS = Links(patch = PATCH)

        private val LAUNCH_RESPONSE = LaunchResponse(
            links = LINKS,
            success = MISSION_SUCCESS,
            name = NAME,
            date_unix = DATE
        )
    }
}
