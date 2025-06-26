package dev.rhcehd123.dailylog.data.mapper

import dev.rhcehd123.dailylog.data.model.Content as DomainContent
import dev.rhcehd123.dailylog.data.room.model.Content as EntityContent

object ModelMapper {
    fun DomainContent.toEntity(): EntityContent {
        return EntityContent(
            uid = 0,
            date = date,
            comment = comment
        )
    }

    fun EntityContent.toDomain(): DomainContent {
        return DomainContent(
            date = date,
            comment = comment ?: ""
        )
    }
}