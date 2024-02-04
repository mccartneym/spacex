package uk.co.bits.spacex.util

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher

@ExperimentalCoroutinesApi
class TestSchedulerProvider(
    private val dispatcher: TestDispatcher = StandardTestDispatcher()
) : SchedulerProvider {

    override fun default(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun main(): Scheduler {
        return Schedulers.trampoline()
    }
}
