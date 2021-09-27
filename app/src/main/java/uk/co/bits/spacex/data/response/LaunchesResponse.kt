package uk.co.bits.spacex.data.response

//import com.dojo.pay.data.repository.ResponseBase
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LaunchesResponse(val transactionId: String)// : ResponseBase()
