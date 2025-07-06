package dev.rhcehd123.dailylog.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.rhcehd123.dailylog.data.room.model.DailyLog
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyLogDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(dailyLog: DailyLog): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAll(vararg dailyLogs: DailyLog): List<Long>

    @Query("SELECT * FROM daily_logs WHERE task_id = :taskId")
    fun getAllByTaskId(taskId: Long): Flow<List<DailyLog>>

    @Query("SELECT * FROM daily_logs")
    fun getAll(): Flow<List<DailyLog>>
}