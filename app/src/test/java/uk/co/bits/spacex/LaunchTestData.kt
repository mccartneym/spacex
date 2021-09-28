package uk.co.bits.spacex

import uk.co.bits.spacex.data.response.LaunchResponse
import uk.co.bits.spacex.data.response.Links
import uk.co.bits.spacex.data.response.Patch

const val URL1 = "smallImageUrl1"
const val MISSION_SUCCESS1 = false
const val NAME1 = "FalconSat"
const val DATE1 = 1142553600L
const val PARSED_DATE1 = "24-03-2006"

val PATCH1 = Patch(small = URL1)
val LINKS1 = Links(patch = PATCH1)

val LAUNCH_RESPONSE1 = LaunchResponse(
    links = LINKS1,
    success = MISSION_SUCCESS1,
    name = NAME1,
    date_unix = DATE1
)

const val URL2 = "smallImageUrl2"
const val MISSION_SUCCESS2 = false
const val NAME2 = "DemoSat"
const val DATE2 = 1174439400L
const val PARSED_DATE2 = "21-03-2007"

val PATCH2 = Patch(small = URL2)
val LINKS2 = Links(patch = PATCH2)

val LAUNCH_RESPONSE2 = LaunchResponse(
    links = LINKS2,
    success = MISSION_SUCCESS2,
    name = NAME2,
    date_unix = DATE2
)

const val URL3 = "smallImageUrl3"
const val MISSION_SUCCESS3 = false
const val NAME3 = "Trailblazer"
const val DATE3 = 1217734440L
const val PARSED_DATE3 = "03-08-2008"

val PATCH3 = Patch(small = URL3)
val LINKS3 = Links(patch = PATCH3)

val LAUNCH_RESPONSE3 = LaunchResponse(
    links = LINKS3,
    success = MISSION_SUCCESS3,
    name = NAME3,
    date_unix = DATE3
)

//val LAUNCH1 = Launch("url1", false, "launch name1", "04-06-2010")
//val LAUNCH2 = Launch("url2", true, "launch name2", "05-07-2011")
//val LAUNCH3 = Launch("url3", false, "launch name3", "06-08-2012")
//
//val LAUNCH_LIST = listOf(LAUNCH1, LAUNCH2, LAUNCH3)

val LAUNCH_RESPONSE_LIST = listOf(LAUNCH_RESPONSE1, LAUNCH_RESPONSE2, LAUNCH_RESPONSE3)
