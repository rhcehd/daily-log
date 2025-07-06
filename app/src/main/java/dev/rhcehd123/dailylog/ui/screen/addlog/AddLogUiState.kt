package dev.rhcehd123.dailylog.ui.screen.addlog

import dev.rhcehd123.dailylog.ui.screen.addlog.state.AddDailyLogResultState

data class AddLogUiState (
    val date: Long = 0L,
    val text: String = "",
    val addDailyLogResultState: AddDailyLogResultState = AddDailyLogResultState.Idle
)