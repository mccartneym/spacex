package uk.co.bits.spacex.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import uk.co.bits.spacex.data.api.LaunchesApiService
import uk.co.bits.spacex.data.mapper.LaunchMapper
import uk.co.bits.spacex.data.repository.LaunchesRepository
import uk.co.bits.spacex.data.repository.SpaceXLaunchesRepository
import uk.co.bits.spacex.data.service.LaunchesService
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    private const val BASE_URL = "https://api.spacexdata.com/v4/"
    private const val READ_TIMEOUT: Long = 30000 // 30 seconds

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient().newBuilder()
            .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
            .build()

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    fun provideLaunchesApiService(retrofit: Retrofit): LaunchesApiService {
        return retrofit.create(LaunchesApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesRepository(
        launchesService: LaunchesService,
        mapper: LaunchMapper,
    ): LaunchesRepository {
        return SpaceXLaunchesRepository(launchesService, mapper)
    }
}
