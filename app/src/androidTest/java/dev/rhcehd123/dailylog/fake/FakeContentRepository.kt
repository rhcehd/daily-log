package dev.rhcehd123.dailylog.fake

import dev.rhcehd123.dailylog.data.model.Content
import dev.rhcehd123.dailylog.data.repository.ContentRepository
import dev.rhcehd123.dailylog.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

class FakeContentRepository(var isEmpty: Boolean = false): ContentRepository {
    override suspend fun addContent(content: Content): Boolean {
        return true
    }

    override val contentsFlow: Flow<DataState<List<Content>>>
        get() = if(isEmpty) {
            emptyFlow()
        } else {
            flowOf(DataState.Success(emptyList()))
        }
}