package uk.co.bits.spacex.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.co.bits.spacex.util.AndroidDispatcherProvider
import uk.co.bits.spacex.util.AndroidSchedulerProvider
import uk.co.bits.spacex.util.DispatcherProvider
import uk.co.bits.spacex.util.SchedulerProvider

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = AndroidDispatcherProvider

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = AndroidSchedulerProvider

}
