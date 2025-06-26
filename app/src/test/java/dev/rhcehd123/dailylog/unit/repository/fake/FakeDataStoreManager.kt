package dev.rhcehd123.dailylog.unit.repository.fake

import dev.rhcehd123.dailylog.data.datastore.DataStoreManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeDataStoreManager: DataStoreManager {
    private val dataStore = mutableMapOf<String, Any>()
    private val _contentTypeFlow = MutableStateFlow("")

    companion object {
        const val CONTENT_TYPE_KEY = "content_type"
    }

    override suspend fun saveContentType(contentType: String) {
        dataStore[CONTENT_TYPE_KEY] = contentType
        _contentTypeFlow.emit(contentType)
    }

    override val contentTypeFlow: Flow<String> = _contentTypeFlow
}