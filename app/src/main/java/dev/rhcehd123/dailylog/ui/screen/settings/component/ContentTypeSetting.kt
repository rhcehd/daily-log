package dev.rhcehd123.dailylog.ui.screen.settings.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import dev.rhcehd123.dailylog.R
import dev.rhcehd123.dailylog.ui.model.ContentType
import dev.rhcehd123.dailylog.ui.screen.settings.Divider
import dev.rhcehd123.dailylog.ui.screen.settings.SettingsUiState
import dev.rhcehd123.dailylog.ui.theme.DailyLogTheme
import dev.rhcehd123.dailylog.utils.TestTag

@Composable
fun ContentTypeSetting(
    uiState: SettingsUiState,
    onChangeContentType: (ContentType) -> Unit
) {
    var contentType by remember { mutableStateOf(uiState.contentType) }
    Column(
        modifier = Modifier.testTag(TestTag.ContentTypeSetting)
    ) {
        Text(
            text = stringResource(R.string.content_type)
        )
        DailyLogRadioButton(
            testTag = "${TestTag.ContentTypeSetting}_1",
            text = stringResource(R.string.calendar_type),
            onClick = {
                contentType = ContentType.CalendarType
                onChangeContentType(ContentType.CalendarType)
            },
            selected = contentType == ContentType.CalendarType
        )
        DailyLogRadioButton(
            testTag = "${TestTag.ContentTypeSetting}_2",
            text = stringResource(R.string.list_type),
            onClick = {
                contentType = ContentType.ListType
                onChangeContentType(ContentType.ListType)
            },
            selected = contentType == ContentType.ListType
        )
        Divider()
    }
}

@Composable
fun DailyLogRadioButton(
    testTag: String = "",
    text: String,
    onClick: () -> Unit,
    selected: Boolean
) {
    Row(
        modifier = Modifier
            .testTag(testTag)
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = { onClick() }
        )
        Text(
            text = text,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
@Preview
fun ContentTypeSettingPreview() {
    DailyLogTheme {
        ContentTypeSetting(
            uiState = SettingsUiState(),
            onChangeContentType = {}
        )
    }
}

@Composable
@Preview
fun DailyLogRadioButtonPreview() {
    DailyLogTheme {
        DailyLogRadioButton(
            text = "테스트",
            onClick = {},
            selected = true
        )
    }
}