package dev.rhcehd123.dailylog.ui.screen.addlog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rhcehd123.dailylog.data.repository.DailyLogRepository
import dev.rhcehd123.dailylog.ui.screen.addlog.state.AddDailyLogResultState
import dev.rhcehd123.dailylog.utils.Utils.currentLocalTimeMillis
import dev.rhcehd123.dailylog.utils.Utils.toDayNumber
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddLogViewModel @Inject constructor(
    private val dailyLogRepository: DailyLogRepository
): ViewModel() {
    companion object {
        const val MAX_COMMENT_LENGTH = 200
    }

    private val _uiState = MutableStateFlow(AddLogUiState(date = currentLocalTimeMillis().toDayNumber()))
    val uiState = _uiState.asStateFlow()

    private val _selectedTaskId = MutableStateFlow(0L)
    val selectedTaskId = _selectedTaskId.asStateFlow()

    fun updateDate(date: Long) {
        _uiState.value = _uiState.value.copy(
            date = date
        )
    }

    fun updateComment(text: String) {
        _uiState.value = _uiState.value.copy(
            text = text
        )
    }

    fun updateSelectedTaskId(taskId: Long) {
        _selectedTaskId.update { taskId }
    }

    fun addContent() {
        val date = _uiState.value.date
        val text = _uiState.value.text
        val taskId = _selectedTaskId.value

        viewModelScope.launch {
            val result = dailyLogRepository.addDailyLog(taskId, date, text)
            if(!result) {
                _uiState.update {
                    _uiState.value.copy(
                        addDailyLogResultState = AddDailyLogResultState.Failure()
                    )
                }
            } else {
                _uiState.update {
                    _uiState.value.copy(
                        addDailyLogResultState = AddDailyLogResultState.Success()
                    )
                }
            }
        }
    }
}