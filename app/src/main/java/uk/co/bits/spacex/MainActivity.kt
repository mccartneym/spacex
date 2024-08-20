package uk.co.bits.spacex

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
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
                    composable("launchList") {
                        LaunchListScreen(
                            onViewLaunchClicked = ::playVideo
                        )
                    }
                }
            }
        }
    }

    private fun playVideo(videoId: String?) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId"))
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$videoId") )

        try {
            startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            startActivity(webIntent)
        }
    }
}
