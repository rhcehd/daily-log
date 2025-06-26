package dev.rhcehd123.dailylog.ui.screen.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import dev.rhcehd123.dailylog.data.model.Content
import dev.rhcehd123.dailylog.ui.theme.DailyLogTheme
import dev.rhcehd123.dailylog.utils.TestTag
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DailyLogCalendar(
    contents: List<Content>
) {
    val contentsSet = remember(contents) { contents.toSet() }
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) } // Adjust as needed
    val endMonth = remember { currentMonth.plusMonths(100) } // Adjust as needed
    val calendarState = rememberCalendarState(
        firstVisibleMonth = currentMonth,
        startMonth = startMonth,
        endMonth = endMonth,
    )
    val daysOfWeek = remember { daysOfWeek() }

    var showDetailCard by remember { mutableStateOf(false) }
    var detailCardOffset by remember { mutableStateOf(Offset.Zero) }
    var detailCardText by remember { mutableStateOf("") }

    HorizontalCalendar(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures {
                    if(showDetailCard) {
                        showDetailCard = false
                    }
                }
            }
            .testTag(TestTag.ContentCalendar),
        state = calendarState,
        //calendarScrollPaged = true,
        //userScrollEnabled = true,
        monthHeader = {
            Column {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 16.dp),
                    text = it.yearMonth.toString(),
                    fontSize = TextUnit(30f, TextUnitType.Sp)
                )
                DaysOfWeekTitle(daysOfWeek = daysOfWeek)
            }
        },
        dayContent = { calendarDay ->
            Day(
                calendarDay = calendarDay,
                checkedDate = contentsSet.map { it.date }.toSet(),
                onShowPopup = { offset ->
                    detailCardOffset = offset
                    showDetailCard = true
                    detailCardText = contentsSet.find {
                        it.date == calendarDay.date.toEpochDay()
                    }?.comment ?: ""
                }
            )
        }
    )

    if(showDetailCard) {
        DetailCard(
            offset = detailCardOffset,
            onDismiss = { showDetailCard = false },
            text = detailCardText
        )
    }
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            )
        }
    }
}

@Composable
fun Day(
    calendarDay: CalendarDay,
    checkedDate: Set<Long>,
    onShowPopup: (Offset) -> Unit,
) {
    var offset by remember { mutableStateOf(Offset.Zero) }
    val interactionSource = remember { MutableInteractionSource() }
    val checkedDay = checkedDate.contains(calendarDay.date.toEpochDay())
    Box(
        modifier = if (checkedDay) {
            Modifier
                .aspectRatio(9 / 16f)
                .onGloballyPositioned {
                    offset = it.positionInWindow()
                }
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    onShowPopup(offset)
                }
        } else {
            Modifier
                .aspectRatio(9 / 16f)
        }
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.TopStart),
            text = calendarDay.date.dayOfMonth.toString(),
            color = if (calendarDay.position == DayPosition.MonthDate) Color.Black else Color.LightGray
        )
        if (checkedDay) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                imageVector = Icons.Default.Done,
                colorFilter = if (calendarDay.position == DayPosition.MonthDate) null else ColorFilter.tint(
                    Color.LightGray
                ),
                contentDescription = "done"
            )
        }
    }
}

@Composable
@Preview
fun DailyLogCalendarPreview() {
    DailyLogTheme {
        DailyLogCalendar(
            contents = emptyList()
        )
    }
}
