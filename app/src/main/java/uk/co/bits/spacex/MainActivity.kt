package uk.co.bits.spacex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import uk.co.bits.spacex.ui.LaunchListScreen
import uk.co.bits.spacex.ui.theme.SpaceXTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SpaceXTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "launchList") {
                    composable("launchList") { LaunchListScreen() }
                }
            }
        }
    }
}
