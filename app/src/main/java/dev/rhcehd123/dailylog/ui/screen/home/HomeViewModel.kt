package dev.rhcehd123.dailylog.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rhcehd123.dailylog.data.model.Content
import dev.rhcehd123.dailylog.data.repository.ContentRepository
import dev.rhcehd123.dailylog.data.repository.SettingsRepository
import dev.rhcehd123.dailylog.ui.model.ContentType
import dev.rhcehd123.dailylog.utils.DataState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val contentRepository: ContentRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val contentsStateFlow = contentRepository.contentsFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = DataState.Loading
    )
    private val contentTypeStateFlow = settingsRepository.contentTypeFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = DataState.Loading
        )

    val uiState =
        contentsStateFlow.combine(contentTypeStateFlow) { contentsState, contentTypeState ->
            var isLoading = false
            var contentType = ContentType.CalendarType
            var contents = emptyList<Content>()
            when (contentsState) {
                is DataState.Loading -> { isLoading = true }
                is DataState.Success -> { contents = contentsState.data }
                is DataState.Error -> {  }
            }
            when (contentTypeState) {
                is DataState.Loading -> { isLoading = true }
                is DataState.Success -> { contentType = ContentType.fromValue(contentTypeState.data) }
                is DataState.Error -> {  }
            }
            HomeUiState(
                isLoading = isLoading,
                contents = contents,
                contentType = contentType
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = HomeUiState(isLoading = true)
        )

    /*init {
        viewModelScope.launch {
            (contentRepository as? ContentRepositoryImpl)?.clearAllDataForTest()
        }
    }*/
}