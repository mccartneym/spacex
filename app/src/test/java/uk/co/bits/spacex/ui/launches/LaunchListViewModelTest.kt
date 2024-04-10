package uk.co.bits.spacex.ui.launches

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    private val listViewStateObserver: Observer<LaunchListViewState> = mockk(relaxed = true)
    private val getLaunchesUseCase: GetLaunchesUseCase = mockk()
    private val provider: DispatcherProvider = TestDispatcherProvider()
    private lateinit var sut: LaunchListViewModel

    @Before
    fun setUp() {
        sut = LaunchListViewModel(getLaunchesUseCase, provider)
        sut.listViewState.observeForever(listViewStateObserver)
    }

    @Test
    fun `when loading launches results in error, then send error view state`() = runTest(provider.main()) {
        coEvery { getLaunchesUseCase() } returns Result.failure(SpaceXLaunchesRepository.UnableToLoadLaunchesError())

        sut.onStart(mockk())
        advanceUntilIdle()

        verifySequence {
            listViewStateObserver.onChanged(LaunchListViewState.ListLoading)
            listViewStateObserver.onChanged(LaunchListViewState.ListError)
        }
    }

    @Test
    fun `when loading launches results in empty list, then send empty view state`() = runTest(provider.main()) {
        coEvery { getLaunchesUseCase() } returns Result.success(emptyList())

        sut.onStart(mockk())
        advanceUntilIdle()

        verifySequence {
            listViewStateObserver.onChanged(LaunchListViewState.ListLoading)
            listViewStateObserver.onChanged(LaunchListViewState.ListEmpty)
        }
    }

    @Test
    fun `when loading results in list with content, then send has content view state`() = runTest(provider.main()) {
        coEvery { getLaunchesUseCase() } returns Result.success(LAUNCH_LIST)

        sut.onStart(mockk())
        advanceUntilIdle()

        verifySequence {
            listViewStateObserver.onChanged(LaunchListViewState.ListLoading)
            listViewStateObserver.onChanged(LaunchListViewState.ListHasContent(LAUNCH_LIST))
        }
    }

}
