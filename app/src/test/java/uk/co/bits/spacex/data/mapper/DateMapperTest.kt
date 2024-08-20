package uk.co.bits.spacex.data.mapper

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import uk.co.bits.spacex.data.mapper.DateMapper

class DateMapperTest {

    private lateinit var sut: DateMapper

    @Before
    fun setUp() {
        sut = DateMapper()
    }

    @Test
    fun `given unix date 1143239400, when converted to dd-MM-yyyy, then parse to 24-03-2006`() {
        val result = sut.parseUnixDate(1143239400)

        assertEquals("24-03-2006", result)
    }

    @Test
    fun `given unix date 1174439400, when converted to dd-MM-yyyy, then parse to 21-03-2007`() {
        val result = sut.parseUnixDate(1174439400)

        assertEquals("21-03-2007", result)
    }

    @Test
    fun `given unix date 1217734440, when converted to dd-MM-yyyy, then parse to 03-08-2007`() {
        val result = sut.parseUnixDate(1217734440)

        assertEquals("03-08-2008", result)
    }

}
