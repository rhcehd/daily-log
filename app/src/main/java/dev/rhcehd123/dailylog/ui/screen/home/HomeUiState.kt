package dev.rhcehd123.dailylog.ui.screen.home

import dev.rhcehd123.dailylog.data.model.Content
import dev.rhcehd123.dailylog.ui.model.ContentType

data class HomeUiState(
    val isLoading: Boolean = false,
    val contents: List<Content> = emptyList(),
    val contentType: ContentType = ContentType.CalendarType,
)