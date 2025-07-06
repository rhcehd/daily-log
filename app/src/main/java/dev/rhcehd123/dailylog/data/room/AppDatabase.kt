package dev.rhcehd123.dailylog.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.rhcehd123.dailylog.data.room.dao.DailyLogDao
import dev.rhcehd123.dailylog.data.room.dao.DailyTaskDao
import dev.rhcehd123.dailylog.data.room.model.DailyLog
import dev.rhcehd123.dailylog.data.room.model.DailyTask

@Database(entities = [DailyLog::class, DailyTask::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun dailyLogDao(): DailyLogDao
    abstract fun dailyTaskDao(): DailyTaskDao

    fun addDailyLog(dailyLog: DailyLog) {
        dailyLogDao().insert(dailyLog)
    }

    fun addDailyTask(dailyTask: DailyTask) {
        dailyTaskDao().insert(dailyTask)
    }

    fun getAllDailyLog() = dailyLogDao().getAll()

    fun getAllDailyTask() = dailyTaskDao().getAll()

}