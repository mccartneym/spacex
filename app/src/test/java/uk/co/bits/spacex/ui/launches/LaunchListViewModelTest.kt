package uk.co.bits.spacex.ui.launches

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
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

    private val getLaunches: GetLaunchesUseCase = mockk()
    private val provider: DispatcherProvider = TestDispatcherProvider()
    private val sut: LaunchListViewModel by lazy { LaunchListViewModel(getLaunches, provider) }

    @Test
    fun `when loading launches results in error, then send error view state`() = runTest(provider.main()) {
        coEvery { getLaunches() } returns Result.failure(SpaceXLaunchesRepository.UnableToLoadLaunchesError())

        val result = mutableListOf<LaunchListViewState>()
        sut.listViewState.take(2).toList(result)

        assertThat(result).isEqualTo(listOf(LaunchListViewState.ListLoading, LaunchListViewState.ListError))
    }

    @Test
    fun `when loading launches results in empty list, then send empty view state`() = runTest(provider.main()) {
        coEvery { getLaunches() } returns Result.success(emptyList())

        val result = mutableListOf<LaunchListViewState>()
        sut.listViewState.take(2).toList(result)

        assertThat(result).isEqualTo(listOf(LaunchListViewState.ListLoading, LaunchListViewState.ListEmpty))
    }

    @Test
    fun `when loading results in list with content, then send has content view state`() = runTest(provider.main()) {
        coEvery { getLaunches() } returns Result.success(LAUNCH_LIST)

        val result = mutableListOf<LaunchListViewState>()
        sut.listViewState.take(2).toList(result)

        assertThat(result).isEqualTo(listOf(LaunchListViewState.ListLoading, LaunchListViewState.ListHasContent(LAUNCH_LIST)))
    }

}
