package dev.rhcehd123.dailylog.ui.screen.drawer

import dev.rhcehd123.dailylog.data.model.DailyTask
import dev.rhcehd123.dailylog.ui.screen.drawer.state.AddDailyTaskResultState

data class DrawerUiState(
    val isLoading: Boolean = false,
    val dailyTasks: List<DailyTask> = emptyList(),
    val selectedTaskId: Long = 0L,
    val addDailyTaskResultState: AddDailyTaskResultState = AddDailyTaskResultState.Idle
)