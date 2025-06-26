package dev.rhcehd123.dailylog.unit.repository.fake

import dev.rhcehd123.dailylog.data.room.dao.ContentDao
import dev.rhcehd123.dailylog.data.room.model.Content
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeContentDao: ContentDao {
    private val contents = mutableListOf<Content>()

    override fun insert(content: Content): Long {
        contents.add(content)
        return content.uid.toLong()
    }

    override fun insertAll(vararg contents: Content): List<Long> {
        this.contents.addAll(contents)
        return contents.map { it.uid.toLong() }
    }

    override fun getAll(): Flow<List<Content>> {
        return flowOf(contents)
    }
}