package dev.rhcehd123.dailylog.ui.screen.addlog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.rhcehd123.dailylog.ui.screen.addlog.AddLogViewModel.Companion.MAX_COMMENT_LENGTH
import dev.rhcehd123.dailylog.ui.screen.addlog.component.DatePickerDialog
import dev.rhcehd123.dailylog.utils.Utils.toDayNumber
import dev.rhcehd123.dailylog.utils.Utils.toDateString
import dev.rhcehd123.dailylog.ui.screen.addlog.state.AddDailyLogResultState
import dev.rhcehd123.dailylog.ui.theme.DailyLogTheme
import dev.rhcehd123.dailylog.utils.TestTag
import dev.rhcehd123.dailylog.utils.Utils.currentLocalTimeMillis
import dev.rhcehd123.dailylog.utils.Utils.toLocalTimeMillis

@Composable
fun AddLogScreen(
    viewModel: AddLogViewModel = hiltViewModel(),
    taskId: Long,
    onNavigateToHome: () -> Unit,
    onShowSnackbar: (String) -> Unit,
    onChangeFabSlot: (@Composable () -> Unit) -> Unit
) {
    LaunchedEffect(Unit) {
        onChangeFabSlot({})
        viewModel.updateSelectedTaskId(taskId)
    }

    val uiState by viewModel.uiState.collectAsState()
    AddLogScreen(
        onDismiss = onNavigateToHome,
        onAddLog = viewModel::addContent,
        onDateChange = viewModel::updateDate,
        onCommentChange = viewModel::updateComment,
    )

    val state = uiState.addDailyLogResultState
    LaunchedEffect(state) {
        when (state) {
            is AddDailyLogResultState.Failure -> {
                onShowSnackbar(state.message)
            }

            is AddDailyLogResultState.Success -> {
                onShowSnackbar(state.message)
                onNavigateToHome()
            }

            is AddDailyLogResultState.Idle -> {
                //do nothing
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLogScreen(
    onDismiss: () -> Unit,
    onAddLog: () -> Unit,
    onDateChange: (Long) -> Unit,
    onCommentChange: (String) -> Unit,
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = currentLocalTimeMillis())
    var selectedDate by rememberSaveable { mutableStateOf(datePickerState.selectedDateMillis.toDateString()) }
    var comment by rememberSaveable { mutableStateOf("") }
    var commentLength by rememberSaveable { mutableIntStateOf(0) }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
        ) {
            Spacer(
                Modifier.height((0.1f * LocalConfiguration.current.screenHeightDp).dp)
            )
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "Date : "
                )
                Box(
                    modifier = Modifier
                        .testTag(TestTag.DateField)
                        .fillMaxWidth(0.5f)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) { showDatePicker = true }
                ) {
                    Text(
                        modifier = Modifier,
                        text = selectedDate
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .wrapContentWidth()
                            .align(Alignment.BottomCenter),
                        thickness = 2.dp,
                    )
                }
            }
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterStart),
                    text = "Comment : "
                )
                Text(
                    modifier = Modifier
                        .testTag(TestTag.CommentCount)
                        .align(Alignment.CenterEnd),
                    text = "$commentLength/$MAX_COMMENT_LENGTH"
                )
            }
            OutlinedTextField(
                modifier = Modifier
                    .testTag(TestTag.CommentField)
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f),
                value = comment,
                onValueChange = {
                    if (it.length <= MAX_COMMENT_LENGTH) {
                        comment = it
                        commentLength = it.length
                        onCommentChange(it)
                    }
                }
            )
            Row(
                modifier = Modifier
                    .align(Alignment.End)
                //.padding(end = 16.dp)
            ) {
                Button(
                    modifier = Modifier.testTag(TestTag.CloseButton),
                    onClick = {
                        keyboardController?.hide()
                        onDismiss()
                    }
                ) {
                    Text(text = "닫기")
                }
                Button(
                    modifier = Modifier.testTag(TestTag.AddButton),
                    onClick = {
                        keyboardController?.hide()
                        if (selectedDate.isEmpty()) {
                            return@Button
                        } else {
                            onAddLog()
                        }
                    }
                ) {
                    Text(text = "추가")
                }
            }
        }
    }
    if (showDatePicker) {
        DatePickerDialog(
            datePickerState = datePickerState,
            onDismiss = {
                val localTimeMillis = datePickerState.selectedDateMillis?.toLocalTimeMillis()
                showDatePicker = false
                selectedDate = localTimeMillis.toDateString()
                onDateChange(localTimeMillis.toDayNumber())
            }
        )
    }
}

@Composable
@Preview
fun AddContentScreenPreview() {
    DailyLogTheme {
        AddLogScreen(
            onAddLog = {},
            onDismiss = {},
            onDateChange = {},
            onCommentChange = {}
        )
    }
}

