package uk.co.bits.spacex.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider(
    val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
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
