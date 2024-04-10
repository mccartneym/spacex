package uk.co.bits.spacex.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.co.bits.spacex.ui.LaunchListScreen

@Composable
internal fun LaunchesNavHost() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "launchList") {
        composable("profile") {
            LaunchListScreen()
        }
    }
}
