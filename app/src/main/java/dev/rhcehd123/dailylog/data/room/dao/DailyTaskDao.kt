package dev.rhcehd123.dailylog.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.rhcehd123.dailylog.data.room.model.DailyTask
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyTaskDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(dailyTask: DailyTask): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAll(vararg dailyTasks: DailyTask): List<Long>

    @Query("SELECT * FROM daily_tasks WHERE id = :id")
    fun getById(id: Long): DailyTask?

    @Query("SELECT * FROM daily_tasks ORDER BY id ASC LIMIT 1")
    fun getFirst(): DailyTask?

    @Query("SELECT * FROM daily_tasks")
    fun getAll(): Flow<List<DailyTask>>
}