package uk.co.bits.spacex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import uk.co.bits.spacex.ui.launches.LaunchListFragment


@AndroidEntryPoint
//class MainActivity : ComponentActivity() {
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<LaunchListFragment>(R.id.container)
            }
        }
    }
}
