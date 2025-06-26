package dev.rhcehd123.dailylog.ui.screen.addcontent

import dev.rhcehd123.dailylog.ui.state.AddContentResultState

data class AddContentUiState (
    val date: Long = 0L,
    val text: String = "",
    val addContentResultState: AddContentResultState = AddContentResultState.Idle
)