package dev.rhcehd123.dailylog.ui.screen.home

import dev.rhcehd123.dailylog.data.model.DailyLog
import dev.rhcehd123.dailylog.data.model.DailyTask
import dev.rhcehd123.dailylog.ui.model.ContentType

data class HomeUiState(
    val isLoading: Boolean = false,
    val dailyTask: DailyTask = DailyTask(name = ""),
    val dailyLogs: List<DailyLog> = emptyList(),
    val contentType: ContentType = ContentType.CalendarType,
)