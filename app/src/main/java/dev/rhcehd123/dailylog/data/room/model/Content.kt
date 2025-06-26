package dev.rhcehd123.dailylog.data.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Content(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val date: Long,
    val comment: String?,
)