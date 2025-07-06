package dev.rhcehd123.dailylog.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.rhcehd123.dailylog.navigation.DailyLogRoute
import dev.rhcehd123.dailylog.ui.screen.drawer.DailyLogDrawer
import dev.rhcehd123.dailylog.utils.TestTag
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyLogScaffold(
    scope: CoroutineScope? = null,
    navController: NavHostController? = null,
    snackbarHostState: SnackbarHostState? = null,
    fabSlot: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val onShowSnackbar: (String) -> Unit =
        remember { { msg -> scope?.launch { snackbarHostState?.showSnackbar(msg) } } }
    val onNavigateToHomeWithTask: () -> Unit = remember {
        {
            if (navController != null) {
                val currentRoute = navController.currentBackStackEntry?.destination?.route
                if (currentRoute == DailyLogRoute.ADD_LOG) {
                    navController.popBackStack()
                }
            }
            scope?.launch {
                drawerState.close()
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DailyLogDrawer(
                onShowSnackbar = onShowSnackbar,
                onNavigateToHome = onNavigateToHomeWithTask
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        //Text(stringResource(R.string.app_name))
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { scope?.launch { drawerState.open() } }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        IconButton(
                            modifier = Modifier.testTag(TestTag.SettingsButton),
                            onClick = {
                                navController?.navigate(DailyLogRoute.SETTINGS) {
                                    launchSingleTop = true
                                }
                            }
                        ) {
                            Icon(Icons.Default.Settings, contentDescription = "Settings")
                        }
                    }
                )
            },
            snackbarHost = {
                if (snackbarHostState != null) {
                    SnackbarHost(snackbarHostState)
                }
            },
            floatingActionButton = {
                fabSlot?.invoke()
            }
        ) { paddingValue ->
            Box(
                modifier = Modifier
                    .padding(paddingValue)
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
            ) {
                content.invoke()
            }
        }
    }
}