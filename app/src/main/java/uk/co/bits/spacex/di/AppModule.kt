package uk.co.bits.spacex.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.co.bits.spacex.util.AndroidDispatcherProvider
import uk.co.bits.spacex.util.DispatcherProvider

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = AndroidDispatcherProvider

}
