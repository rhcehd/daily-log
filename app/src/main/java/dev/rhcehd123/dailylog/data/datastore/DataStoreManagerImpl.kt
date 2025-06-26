package dev.rhcehd123.dailylog.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DataStoreManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
): DataStoreManager {
    private val dataStore = context.dataStore
    companion object {
        val CONTENT_TYPE_KEY = stringPreferencesKey("content_type")
    }

    override suspend fun saveContentType(contentType: String) {
        dataStore.edit {
            it[CONTENT_TYPE_KEY] = contentType
        }
    }

    override val contentTypeFlow = dataStore.data
        .map { it[CONTENT_TYPE_KEY] ?: "" }
}