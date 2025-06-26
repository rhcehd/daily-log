package dev.rhcehd123.dailylog.data.repository

import dev.rhcehd123.dailylog.utils.DataState
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val contentTypeFlow: Flow<DataState<String>>

    suspend fun saveContentType(contentType: String)
}