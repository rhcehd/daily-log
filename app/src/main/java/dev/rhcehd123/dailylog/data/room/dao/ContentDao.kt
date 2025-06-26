package dev.rhcehd123.dailylog.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.rhcehd123.dailylog.data.room.model.Content
import kotlinx.coroutines.flow.Flow

@Dao
interface ContentDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(content: Content): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAll(vararg contents: Content): List<Long>

    @Query("SELECT * FROM content")
    fun getAll(): Flow<List<Content>>
}