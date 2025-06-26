package dev.rhcehd123.dailylog.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.rhcehd123.dailylog.data.room.dao.ContentDao
import dev.rhcehd123.dailylog.data.room.model.Content

@Database(entities = [Content::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun contentDao(): ContentDao

    fun addContent(content: Content) {
        contentDao().insert(content)
    }

    fun getAllContent() = contentDao().getAll()
}