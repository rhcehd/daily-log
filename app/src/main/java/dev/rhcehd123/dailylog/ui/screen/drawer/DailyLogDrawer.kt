package dev.rhcehd123.dailylog.ui.screen.drawer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.rhcehd123.dailylog.data.model.DailyTask
import dev.rhcehd123.dailylog.ui.screen.drawer.state.AddDailyTaskResultState
import dev.rhcehd123.dailylog.ui.theme.DailyLogTheme

@Composable
fun DailyLogDrawer(
    viewModel: DrawerViewModel = hiltViewModel(),
    onShowSnackbar: (String) -> Unit,
    onNavigateToHome: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    DailyLogDrawer(
        uiState = uiState,
        onNavigateToHome = onNavigateToHome,
        onSelectTask = viewModel::updateSelectedTask,
        onAddDailyTask = viewModel::onAddDailyTask
    )

    val state = uiState.addDailyTaskResultState
    LaunchedEffect(state) {
        when(state) {
            is AddDailyTaskResultState.Idle -> { /*do nothing*/ }
            is AddDailyTaskResultState.Success -> {
                viewModel.updateSelectedTask(state.dailyTask)
                onNavigateToHome()
            }
            is AddDailyTaskResultState.Failure -> {
                onShowSnackbar(state.errorMessage)
            }
        }
    }
}

@Composable
fun DailyLogDrawer(
    uiState: DrawerUiState,
    onNavigateToHome: () -> Unit,
    onSelectTask: (DailyTask) -> Unit,
    onAddDailyTask: (String) -> Unit,
) {
    var showAddDailyTaskDialog by remember { mutableStateOf(false) }
    ModalDrawerSheet {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("drawer header")
        }

        DailyTaskList(
            uiState = uiState,
            onSelectDailyTask = {
                onSelectTask(it)
                onNavigateToHome()
            }
        )

        NavigationDrawerItem(
            label = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "Add"
                    )
                }

            },
            selected = false,
            onClick = {
                showAddDailyTaskDialog = true
            }
        )
    }

    if(showAddDailyTaskDialog) {
        AddDailyTaskDialog(
            onDismiss = { dailyTaskName ->
                showAddDailyTaskDialog = false
                if(dailyTaskName != null) {
                    onAddDailyTask(dailyTaskName)
                }
            }
        )
    }
}

@Composable
fun DailyTaskList(
    uiState: DrawerUiState,
    onSelectDailyTask: (DailyTask) -> Unit,
) {
    val dailyTasks = uiState.dailyTasks
    val selectedTaskId = uiState.selectedTaskId
    dailyTasks.forEach {
        NavigationDrawerItem(
            label = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = it.name
                    )
                }

            },
            selected = it.id == selectedTaskId,
            onClick = {
                onSelectDailyTask(it)
            }
        )
    }
}

@Composable
fun AddDailyTaskDialog(
    onDismiss: (String?) -> Unit,
) {
    var dailyTaskName by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = { onDismiss(null) },
        title = {
            Text("Add Daily Task")
        },
        text = {
            Column {
                OutlinedTextField(
                    value = dailyTaskName,
                    onValueChange = { dailyTaskName = it },
                    label = { Text("Daily Task Name") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onDismiss(dailyTaskName)
                }
            ) {
                Text("ok")
            }
        }
    )
}

@Composable
@Preview
fun DailyLogDrawerPreview() {
    DailyLogTheme {
        DailyLogDrawer(
            uiState = DrawerUiState(),
            onNavigateToHome = {},
            onSelectTask = {},
            onAddDailyTask = {}
        )
    }
}

@Composable
@Preview
fun AddDailyTaskDialogPreview() {
    DailyLogTheme {
        AddDailyTaskDialog(onDismiss = {})
    }
}