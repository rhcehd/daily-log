package dev.rhcehd123.dailylog.ui.screen.addcontent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rhcehd123.dailylog.data.model.Content
import dev.rhcehd123.dailylog.data.repository.ContentRepository
import dev.rhcehd123.dailylog.ui.state.AddContentResultState
import dev.rhcehd123.dailylog.utils.Utils.currentLocalTimeMillis
import dev.rhcehd123.dailylog.utils.Utils.toDayNumber
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddContentViewModel @Inject constructor(
    private val contentRepository: ContentRepository
): ViewModel() {
    companion object {
        const val MAX_COMMENT_LENGTH = 200
    }

    private val _uiState = MutableStateFlow(AddContentUiState(date = currentLocalTimeMillis().toDayNumber()))
    val uiState = _uiState.asStateFlow()

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

    fun addContent() {
        val date = _uiState.value.date
        val text = _uiState.value.text

        viewModelScope.launch {
            val result = contentRepository.addContent(Content(date, text))
            if(!result) {
                _uiState.update {
                    _uiState.value.copy(
                        addContentResultState = AddContentResultState.Failure()
                    )
                }
            } else {
                _uiState.update {
                    _uiState.value.copy(
                        addContentResultState = AddContentResultState.Success()
                    )
                }
            }
        }
    }
}