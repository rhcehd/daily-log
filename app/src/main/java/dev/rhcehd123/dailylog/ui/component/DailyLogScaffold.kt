package dev.rhcehd123.dailylog.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import dev.rhcehd123.dailylog.utils.TestTag

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyLogScaffold(
    snackbarHostState: SnackbarHostState? = null,
    onNavigateToAction: (() -> Unit)? = null,
    fabSlot: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    //Text(stringResource(R.string.app_name))
                },
                /*navigationIcon = {
                    IconButton(
                        onClick = { scope.launch { drawerState.open() }}
                    ) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                }*/
                actions = {
                    IconButton(
                        modifier = Modifier.testTag(TestTag.SettingsButton),
                        onClick = {
                            onNavigateToAction?.invoke()
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