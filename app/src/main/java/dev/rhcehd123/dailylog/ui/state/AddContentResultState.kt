package dev.rhcehd123.dailylog.ui.state

sealed interface AddContentResultState {
    data class Success(val message: String = "등록 성공", val stamp: Long = System.currentTimeMillis()): AddContentResultState
    data class Failure(val message: String = "등록 실패", val stamp: Long = System.currentTimeMillis()): AddContentResultState
    data object Idle: AddContentResultState
}