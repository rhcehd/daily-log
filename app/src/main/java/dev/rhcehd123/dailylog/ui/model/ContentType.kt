package dev.rhcehd123.dailylog.ui.model

enum class ContentType(val value: String) {
    CalendarType("calendar"),
    ListType("list");

    companion object {
        fun fromValue(value: String): ContentType = entries.find { it.value == value } ?: CalendarType
    }
}