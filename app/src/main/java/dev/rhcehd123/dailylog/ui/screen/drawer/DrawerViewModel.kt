package dev.rhcehd123.dailylog.ui.screen.drawer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rhcehd123.dailylog.data.model.DailyTask
import dev.rhcehd123.dailylog.data.repository.DailyTaskRepository
import dev.rhcehd123.dailylog.ui.screen.drawer.state.AddDailyTaskResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawerViewModel @Inject constructor(
    private val dailyTaskRepository: DailyTaskRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(DrawerUiState())
    val uiState = _uiState.asStateFlow()

    private var _selectedTask = MutableStateFlow(DailyTask(0, ""))
    val selectedTask = _selectedTask.asStateFlow()

    private val dailyTasksStateFlow = dailyTaskRepository.dailyTasksFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
            dailyTasksStateFlow.collect { dailyTasks ->
                _uiState.update {
                    _uiState.value.copy(dailyTasks = dailyTasks)
                }
            }
        }
        viewModelScope.launch {
            val dailyTask = dailyTaskRepository.getFirstOrCreateDailyTask()
            _selectedTask.update { dailyTask }
            _uiState.update { _uiState.value.copy(selectedTaskId = dailyTask.id) }
        }
    }

    fun updateSelectedTask(dailyTask: DailyTask) {
        _selectedTask.update { dailyTask }
        _uiState.update { _uiState.value.copy(selectedTaskId = dailyTask.id) }
    }

    fun onAddDailyTask(dailyTaskName: String) {
        viewModelScope.launch {
            try {
                val result = dailyTaskRepository.addDailyTask(dailyTaskName)
                _uiState.update {
                    _uiState.value.copy(
                        addDailyTaskResultState = AddDailyTaskResultState.Success(result)
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    _uiState.value.copy(
                        addDailyTaskResultState = AddDailyTaskResultState.Failure(e.message ?: "")
                    )
                }
            }
        }
    }
}