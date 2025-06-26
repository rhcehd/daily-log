package dev.rhcehd123.dailylog.ui.screen.settings

import dev.rhcehd123.dailylog.ui.model.ContentType

data class SettingsUiState(
    val isLoading: Boolean = false,
    val contentType: ContentType = ContentType.CalendarType,
    val settingErrorMessage: String? = null
)