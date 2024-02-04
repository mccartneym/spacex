package uk.co.bits.spacex.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider(
    private val dispatcher: TestDispatcher = StandardTestDispatcher()
) : DispatcherProvider {

    override fun default(): CoroutineDispatcher {
        return dispatcher
    }

    override fun io(): CoroutineDispatcher {
        return dispatcher
    }

    override fun main(): CoroutineDispatcher {
        return dispatcher
    }
}
