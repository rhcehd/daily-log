package dev.rhcehd123.dailylog.utils

sealed class DataState<out T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error(val exception: Exception) : DataState<Nothing>()
    data object Loading: DataState<Nothing>()
}