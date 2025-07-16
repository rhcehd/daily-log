package dev.rhcehd123.dailylog.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rhcehd123.dailylog.data.model.DailyLog
import dev.rhcehd123.dailylog.data.model.DailyTask
import dev.rhcehd123.dailylog.data.repository.DailyLogRepository
import dev.rhcehd123.dailylog.data.repository.SettingsRepository
import dev.rhcehd123.dailylog.ui.model.ContentType
import dev.rhcehd123.dailylog.utils.DataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dailyLogRepository: DailyLogRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private lateinit var dailyLogStateFlow: StateFlow<List<DailyLog>>
    private val contentTypeStateFlow = settingsRepository.contentTypeFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = DataState.Loading
        )

    private var _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            contentTypeStateFlow.collect { dataState ->
                when(dataState) {
                    is DataState.Loading -> {
                        _uiState.update {
                            _uiState.value.copy(isLoading = true)
                        }
                    }
                    is DataState.Success -> {
                        _uiState.update {
                            _uiState.value.copy(
                                isLoading = false,
                                contentType = ContentType.fromValue(dataState.data)
                            )
                        }
                    }
                    is DataState.Error -> {}
                }
            }
        }
    }

    fun setDailyTask(dailyTask: DailyTask) {
        _uiState.update {
            _uiState.value.copy(dailyTask = dailyTask)
        }
        dailyLogStateFlow = dailyLogRepository.dailyLogsFlow(dailyTask.id)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(500),
                initialValue = emptyList()
            )
        viewModelScope.launch {
            dailyLogStateFlow.collect { dailyLogs ->
                _uiState.update {
                    _uiState.value.copy(dailyLogs = dailyLogs)
                }
            }
        }
    }
}