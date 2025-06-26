package dev.rhcehd123.dailylog.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import dev.rhcehd123.dailylog.utils.TestTag

@Composable
fun TestFab() {
    FloatingActionButton(
        modifier = Modifier.testTag(TestTag.TestFab),
        onClick = {}
    ) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Settings"
        )
    }
}