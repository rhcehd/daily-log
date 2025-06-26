package dev.rhcehd123.dailylog.ui.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rhcehd123.dailylog.data.repository.SettingsRepository
import dev.rhcehd123.dailylog.ui.model.ContentType
import dev.rhcehd123.dailylog.utils.DataState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    val uiState = settingsRepository.contentTypeFlow
        .take(1)
        .map {
            if(it is DataState.Success) {
                SettingsUiState(
                    contentType = when (it.data) {
                        "calendar" -> ContentType.CalendarType
                        "list" -> ContentType.ListType
                        else -> ContentType.CalendarType
                    }
                )
            } else {
                SettingsUiState(
                    isLoading = true,
                    settingErrorMessage = "Failed to load settings"
                )
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = SettingsUiState(isLoading = true)
        )

    fun updateContentType(contentType: ContentType) {
        viewModelScope.launch {
            val contentTypeString = when (contentType) {
                ContentType.CalendarType -> "calendar"
                ContentType.ListType -> "list"
            }
            settingsRepository.saveContentType(contentTypeString)
            // check consistency
        }
    }
}