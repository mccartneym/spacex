package uk.co.bits.spacex.data.mapper

import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class DateMapper @Inject constructor() {
    private val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.UK)

    fun parseUnixDate(unixDate: Long): String {
        return sdf.format(unixDate * 1000)
    }
}
