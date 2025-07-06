package dev.rhcehd123.dailylog.ui.screen.addlog.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import dev.rhcehd123.dailylog.ui.theme.DailyLogTheme
import dev.rhcehd123.dailylog.utils.TestTag
import dev.rhcehd123.dailylog.utils.Utils.currentLocalTimeMillis

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    datePickerState: DatePickerState,
    onDismiss: () -> Unit,
) {
    androidx.compose.material3.DatePickerDialog(
        modifier = Modifier.testTag(TestTag.DatePicker),
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            Button(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("선택")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("취소")
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun DatePickerDialogPreview() {
    DailyLogTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            DatePickerDialog(
                datePickerState = rememberDatePickerState(initialSelectedDateMillis = currentLocalTimeMillis()),
                onDismiss = {}
            )
        }

    }
}