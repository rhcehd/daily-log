package dev.rhcehd123.dailylog.fake

import dev.rhcehd123.dailylog.data.repository.SettingsRepository
import dev.rhcehd123.dailylog.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeSettingsRepository(private val contentType: String = "calendar"): SettingsRepository {
    override val contentTypeFlow: Flow<DataState<String>>
        get() = flowOf(DataState.Success(contentType))

    override suspend fun saveContentType(contentType: String) {

    }
}