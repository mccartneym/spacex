package uk.co.bits.spacex.ui.launches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import uk.co.bits.spacex.ui.theme.SpaceXTheme

@AndroidEntryPoint
class LaunchListFragment : Fragment() {

    private val viewModel: LaunchListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Timber.e("*** onCreateView")
        return ComposeView(requireContext()).apply {
            setContent {
                Timber.e("*** onCreateView, setContent")
                SpaceXTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        LaunchListScreen()
                    }
                }
            }
        }
    }
}
