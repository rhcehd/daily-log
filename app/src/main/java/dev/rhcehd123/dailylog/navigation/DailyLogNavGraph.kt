package dev.rhcehd123.dailylog.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import dev.rhcehd123.dailylog.ui.screen.addlog.AddLogScreen
import dev.rhcehd123.dailylog.ui.screen.home.HomeScreen
import dev.rhcehd123.dailylog.ui.screen.settings.SettingsDialog

object DailyLogRoute {
    const val HOME = "home"
    const val ADD_LOG = "add_log"
    const val SETTINGS = "settings"
}

@Composable
fun DailyLogNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onShowSnackbar: (String) -> Unit,
    onChangeFabSlot: (@Composable () -> Unit) -> Unit,
) {
    val onNavigateToAddLogWithTaskId: (Long) -> Unit = remember { { taskId -> navController.navigate("${DailyLogRoute.ADD_LOG}/$taskId") } }
    val onNavigateToBackStack: () -> Unit = remember { { navController.popBackStack() } }
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = DailyLogRoute.HOME
    ) {
        composable(DailyLogRoute.HOME) {
            HomeScreen(
                onNavigateToAddLogWithTaskId = onNavigateToAddLogWithTaskId,
                onChangeFabSlot = onChangeFabSlot
            )
        }

        composable("${DailyLogRoute.ADD_LOG}/{taskId}") {
            val taskId = it.arguments?.getString("taskId")?.toLong() ?: 0L
            AddLogScreen(
                taskId = taskId,
                onNavigateToHome = onNavigateToBackStack,
                onShowSnackbar = onShowSnackbar,
                onChangeFabSlot = onChangeFabSlot
            )
        }

        dialog(DailyLogRoute.SETTINGS) {
            SettingsDialog(
                onDismiss = onNavigateToBackStack
            )
        }
    }
}