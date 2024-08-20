package uk.co.bits.spacex.data.mapper

import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import uk.co.bits.spacex.*
import uk.co.bits.spacex.data.mapper.DateMapper
import uk.co.bits.spacex.data.mapper.LaunchMapper

class LaunchMapperTest {

    private val dateMapper: DateMapper = mockk()
    private lateinit var sut: LaunchMapper

    @Before
    fun setUp() {
        sut = LaunchMapper(dateMapper)
        every { dateMapper.parseUnixDate(DATE1) } returns PARSED_DATE1
    }

    @Test
    fun `given LaunchResponse object, when parsing to model, then URL is parsed successfully`() {
        val result = sut.toLaunch(LAUNCH_RESPONSE1)

        assertEquals(URL1, result.smallImageUrl)
    }

    @Test
    fun `given LaunchResponse object, when parsing to model, then mission success state is parsed successfully`() {
        val result = sut.toLaunch(LAUNCH_RESPONSE1)

        assertEquals(MISSION_SUCCESS1, result.wasSuccessful)
    }

    @Test
    fun `given LaunchResponse object, when parsing to model, then launch name is parsed successfully`() {
        val result = sut.toLaunch(LAUNCH_RESPONSE1)

        assertEquals(NAME1, result.name)
    }

    @Test
    fun `given LaunchResponse object, when parsing to model, then date is parsed successfully`() {
        val result = sut.toLaunch(LAUNCH_RESPONSE1)

        assertEquals(PARSED_DATE1, result.date)
    }
}
