package dev.rhcehd123.dailylog.data.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreManager {
    suspend fun saveContentType(contentType: String)

    val contentTypeFlow: Flow<String>
}