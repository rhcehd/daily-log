package dev.rhcehd123.dailylog.data.repository.impl

import dev.rhcehd123.dailylog.data.mapper.ModelMapper.toDomain
import dev.rhcehd123.dailylog.data.mapper.ModelMapper.toEntity
import dev.rhcehd123.dailylog.data.model.Content
import dev.rhcehd123.dailylog.data.repository.ContentRepository
import dev.rhcehd123.dailylog.data.room.AppDatabase
import dev.rhcehd123.dailylog.data.room.dao.ContentDao
import dev.rhcehd123.dailylog.utils.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContentRepositoryImpl @Inject constructor(
    private val contentDao: ContentDao,
    private val appDatabase: AppDatabase
): ContentRepository {
    override val contentsFlow: Flow<DataState<List<Content>>> = contentDao
        .getAll()
        .map { it.map { it.toDomain() } }
        .map { DataState.Success(it) }

    override suspend fun addContent(content: Content): Boolean {
        return withContext(Dispatchers.IO) {
            val result = contentDao.insert(content.toEntity())
            result != -1L
        }
    }

    suspend fun clearAllDataForTest() {
        withContext(Dispatchers.IO) {
            appDatabase.clearAllTables()
        }
    }
}