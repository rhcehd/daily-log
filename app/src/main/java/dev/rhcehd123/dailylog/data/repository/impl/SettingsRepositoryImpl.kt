package dev.rhcehd123.dailylog.data.repository.impl

import dev.rhcehd123.dailylog.data.datastore.DataStoreManager
import dev.rhcehd123.dailylog.data.repository.SettingsRepository
import dev.rhcehd123.dailylog.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager
): SettingsRepository {
    override val contentTypeFlow: Flow<DataState<String>> =
        dataStoreManager.contentTypeFlow.map {
            try {
                DataState.Success(it)
            } catch (e: Exception) {
                DataState.Error(e)
            }
        }
    override suspend fun saveContentType(contentType: String) {
        dataStoreManager.saveContentType(contentType)
    }
}