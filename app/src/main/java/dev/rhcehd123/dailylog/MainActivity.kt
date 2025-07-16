package dev.rhcehd123.dailylog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dev.rhcehd123.dailylog.data.room.AppDatabase
import dev.rhcehd123.dailylog.ui.theme.DailyLogTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // for debug
    /*@Inject
    lateinit var appDatabase: AppDatabase*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // for debug
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                //appDatabase.clearAllTables()
            }
        }

        enableEdgeToEdge()
        setContent {
            DailyLogTheme {
                DailyLogApp()
            }
        }
    }
}