package dev.rhcehd123.dailylog.ui.screen.home

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.rhcehd123.dailylog.ui.component.Loading
import dev.rhcehd123.dailylog.ui.model.ContentType
import dev.rhcehd123.dailylog.ui.screen.drawer.DrawerViewModel
import dev.rhcehd123.dailylog.ui.screen.home.component.DailyLogCalendar
import dev.rhcehd123.dailylog.ui.theme.DailyLogTheme
import dev.rhcehd123.dailylog.utils.TestTag
import dev.rhcehd123.dailylog.utils.Utils.toDateString

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    drawerViewModel: DrawerViewModel = hiltViewModel(LocalContext.current as ComponentActivity),
    onNavigateToAddLogWithTaskId: (Long) -> Unit,
    onChangeFabSlot: (@Composable () -> Unit) -> Unit
) {
    val selectedTask by drawerViewModel.selectedTask.collectAsState()
    LaunchedEffect(selectedTask) {
        viewModel.setDailyTask(selectedTask)
    }

    LaunchedEffect(Unit) {
        onChangeFabSlot({
            FloatingActionButton(
                modifier = Modifier.testTag(TestTag.AddDailyLogButton),
                onClick = { onNavigateToAddLogWithTaskId(selectedTask.id) },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        })
    }

    val uiState by viewModel.uiState.collectAsState()
    HomeScreen(
        uiState = uiState,
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
) {
    if (uiState.isLoading) {
        Loading()
    } else {
        when (uiState.contentType) {
            ContentType.CalendarType -> CalendarForm(uiState = uiState)
            ContentType.ListType -> ListForm(uiState = uiState)
        }
    }
}

@Composable
fun CalendarForm(
    uiState: HomeUiState,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        DailyLogCalendar(
            title = uiState.dailyTask.name,
            dailyLogs = uiState.dailyLogs
        )
    }
}

@Composable
fun ListForm(
    uiState: HomeUiState,
) {
    val contents = uiState.dailyLogs
    LazyColumn(
        modifier = Modifier
            .testTag(TestTag.ContentList)
            .fillMaxSize()
    ) {
        items(contents) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(text = "${it.date.toDateString()}: ${it.comment ?: "도망친 곳에 낙원은 없다"}")
            }
        }
    }
}

@Composable
@Preview
fun HomeScreenLoadingPreview() {
    DailyLogTheme {
        HomeScreen(
            uiState = HomeUiState(
                isLoading = true,
                contentType = ContentType.CalendarType,
            )
        )
    }
}

@Composable
@Preview
fun HomeScreenCalendarTypePreview() {
    DailyLogTheme {
        HomeScreen(
            uiState = HomeUiState(
                contentType = ContentType.CalendarType,
                dailyLogs = listOf()
            )
        )
    }
}

@Composable
@Preview
fun HomeScreenListTypePreview() {
    DailyLogTheme {
        HomeScreen(
            uiState = HomeUiState(
                contentType = ContentType.ListType,
                dailyLogs = listOf()
            )
        )
    }
}