package dev.rhcehd123.dailylog.ui.screen.drawer.state

import dev.rhcehd123.dailylog.data.model.DailyTask

sealed interface AddDailyTaskResultState {
    data object Idle: AddDailyTaskResultState
    data class Success(val dailyTask: DailyTask): AddDailyTaskResultState
    data class Failure(val errorMessage: String = "Failure"): AddDailyTaskResultState
}