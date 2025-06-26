package dev.rhcehd123.dailylog.data.repository

import dev.rhcehd123.dailylog.data.model.Content
import dev.rhcehd123.dailylog.utils.DataState
import kotlinx.coroutines.flow.Flow

interface ContentRepository {
    suspend fun addContent(content: Content): Boolean
    val contentsFlow: Flow<DataState<List<Content>>>
}