package uk.co.bits.spacex.ui.launches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import uk.co.bits.spacex.R
import uk.co.bits.spacex.databinding.FragmentLaunchesBinding
import uk.co.bits.spacex.ui.launches.LaunchListViewState.*

@AndroidEntryPoint
class LaunchListFragment : Fragment() {

    private val viewModel: LaunchListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentLaunchesBinding.inflate(inflater)
        binding.observeViewModel()
        return binding.root
    }

    private fun FragmentLaunchesBinding.observeViewModel() {
        viewModel.listViewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                ListLoading -> launchProgressbar.isVisible = true
                ListEmpty, ListError -> launchProgressbar.isVisible = false
                is ListHasContent -> {
                    launchProgressbar.isVisible = false
                    launchList.adapter = LaunchListAdapter(requireContext(), state.launchList)
                }
            }
        }
    }

    companion object {
        fun newInstance(): LaunchListFragment = LaunchListFragment()
    }
}