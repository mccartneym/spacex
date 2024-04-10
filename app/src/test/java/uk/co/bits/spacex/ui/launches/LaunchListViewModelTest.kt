package uk.co.bits.spacex.ui.launches

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import uk.co.bits.spacex.LAUNCH_LIST
import uk.co.bits.spacex.data.repository.SpaceXLaunchesRepository
import uk.co.bits.spacex.domain.usecase.GetLaunchesUseCase
import uk.co.bits.spacex.ui.LaunchListViewModel
import uk.co.bits.spacex.ui.LaunchListViewState
import uk.co.bits.spacex.util.DispatcherProvider
import uk.co.bits.spacex.util.TestDispatcherProvider

@ExperimentalCoroutinesApi
class LaunchListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    //    private val listViewStateObserver: Observer<LaunchListViewState> = mockk(relaxed = true)
    private val getLaunches: GetLaunchesUseCase = mockk()
    private val provider: DispatcherProvider = TestDispatcherProvider()
    private lateinit var sut: LaunchListViewModel

    @Before
    fun setUp() {
        sut = LaunchListViewModel(getLaunches, provider)
//        sut.listViewState.observeForever(listViewStateObserver)
    }

    @Test
    fun `when loading launches results in error, then send error view state`() = runTest(provider.main()) {
        coEvery { getLaunches() } returns Result.failure(SpaceXLaunchesRepository.UnableToLoadLaunchesError())

        sut.onStart(mockk())
        advanceUntilIdle()

        val result = mutableListOf<LaunchListViewState>()
        println("*** 1")
        val result2 = sut.listViewState.first()
        println("*** 1.5: $result2")
        sut.listViewState.take(2).toList(result)
        println("*** 2")

        assertThat(result).isEqualTo(listOf(LaunchListViewState.ListLoading, LaunchListViewState.ListError))
    }

    @Test
    fun `when loading launches results in empty list, then send empty view state`() = runTest(provider.main()) {
        coEvery { getLaunches() } returns Result.success(emptyList())

        sut.onStart(mockk())
        advanceUntilIdle()

//        verifySequence {
//            listViewStateObserver.onChanged(LaunchListViewState.ListLoading)
//            listViewStateObserver.onChanged(LaunchListViewState.ListEmpty)
//        }
    }

    @Test
    fun `when loading results in list with content, then send has content view state`() = runTest(provider.main()) {
        coEvery { getLaunches() } returns Result.success(LAUNCH_LIST)

        sut.onStart(mockk())
        advanceUntilIdle()

//        verifySequence {
//            listViewStateObserver.onChanged(LaunchListViewState.ListLoading)
//            listViewStateObserver.onChanged(LaunchListViewState.ListHasContent(LAUNCH_LIST))
//        }
    }

}
