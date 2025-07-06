package dev.rhcehd123.dailylog

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import dev.rhcehd123.dailylog.navigation.DailyLogNavGraph
import dev.rhcehd123.dailylog.ui.component.DailyLogScaffold
import kotlinx.coroutines.launch

@Composable
fun DailyLogApp() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var fabSlot: @Composable () -> Unit by remember { mutableStateOf({}) }

    val onShowSnackbar: (String) -> Unit = remember { { message -> scope.launch { snackbarHostState.showSnackbar(message) } } }
    val onChangeFabSlot: (@Composable () -> Unit) -> Unit = remember { { fabSlot = it } }

    DailyLogScaffold(
        scope = scope,
        navController = navController,
        snackbarHostState = snackbarHostState,
        fabSlot = fabSlot
    ) {
        DailyLogNavGraph(
            navController = navController,
            onShowSnackbar = onShowSnackbar,
            onChangeFabSlot = onChangeFabSlot
        )
    }
}