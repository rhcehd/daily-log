package dev.rhcehd123.dailylog.ui.screen.addlog.state

sealed interface AddDailyLogResultState {
    data class Success(val message: String = "등록 성공", val stamp: Long = System.currentTimeMillis()): AddDailyLogResultState
    data class Failure(val message: String = "등록 실패", val stamp: Long = System.currentTimeMillis()): AddDailyLogResultState
    data object Idle: AddDailyLogResultState
}