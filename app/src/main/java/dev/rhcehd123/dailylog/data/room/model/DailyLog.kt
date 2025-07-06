package dev.rhcehd123.dailylog.data.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "daily_logs",
    foreignKeys = [
        ForeignKey(
            entity = DailyTask::class,
            parentColumns = ["id"],
            childColumns = ["task_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DailyLog(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "task_id") val taskId: Long,
    val date: Long,
    val comment: String?,
)