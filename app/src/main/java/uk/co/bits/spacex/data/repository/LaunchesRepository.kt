package uk.co.bits.spacex.data.repository

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import uk.co.bits.spacex.data.mapper.LaunchMapper
import uk.co.bits.spacex.data.model.Launch
import uk.co.bits.spacex.data.response.LaunchResponse
import uk.co.bits.spacex.data.service.LaunchesService
import javax.inject.Inject

class LaunchesRepository @Inject constructor(
    private val launchesService: LaunchesService,
    private val mapper: LaunchMapper
) {

    private val launchList: MutableList<Launch> = mutableListOf()

    suspend fun getLaunches(): List<Launch> {
        if (launchList.isEmpty()) {
            loadLaunches()
        }

        return launchList

//        val launch = getTestLaunch() ?: return emptyList()
//        return listOf(launch)
    }

    private suspend fun loadLaunches() {
        val launchesResponse = launchesService.getLaunches()
        if (launchesResponse != null) {
            launchList.clear()
            launchesResponse.forEach { response ->
                launchList.add(mapper.toLaunch(response))
            }
        }
    }

//    private fun getTestLaunch(): Launch? {
//        val json = getTestLaunchJson()
//        val jsonAdapter = getJsonAdapter()
//        val launchResponse: LaunchResponse = jsonAdapter.fromJson(json) ?: return null
//        return mapper.toLaunch(launchResponse)
//    }
//
//    private fun getJsonAdapter(): JsonAdapter<LaunchResponse> {
//        return moshi.adapter(LaunchResponse::class.java)
//    }
}