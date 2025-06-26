package dev.rhcehd123.dailylog.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import dev.rhcehd123.dailylog.ui.screen.addcontent.AddContentScreen
import dev.rhcehd123.dailylog.ui.screen.home.HomeScreen
import dev.rhcehd123.dailylog.ui.screen.settings.SettingsDialog

object WidgetSampleRoute {
    const val MAIN = "main"
    const val ADD_CONTENT = "add_content"
    const val SETTINGS = "settings"
}

@Composable
fun DailyLogNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onShowSnackbar: (String) -> Unit,
    onChangeFabSlot: (@Composable () -> Unit) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = WidgetSampleRoute.MAIN
    ) {
        composable(WidgetSampleRoute.MAIN) {
            HomeScreen(
                onNavigateToAddContent = { navController.navigate(WidgetSampleRoute.ADD_CONTENT) },
                onChangeFabSlot = onChangeFabSlot
            )
        }

        composable(WidgetSampleRoute.ADD_CONTENT) {
            //onChangeFabSlot({})
            AddContentScreen(
                onNavigateToMain = { navController.popBackStack() },
                onShowSnackbar = onShowSnackbar,
                onChangeFabSlot = onChangeFabSlot
            )
        }

        dialog(WidgetSampleRoute.SETTINGS) {
            SettingsDialog(
                onDismiss = { navController.popBackStack() }
            )
        }
    }
}