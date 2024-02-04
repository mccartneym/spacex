package uk.co.bits.spacex.util

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun default(): Scheduler

    fun io(): Scheduler

    fun main(): Scheduler
}
