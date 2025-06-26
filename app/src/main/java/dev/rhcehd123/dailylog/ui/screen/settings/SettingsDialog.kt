package dev.rhcehd123.dailylog.ui.screen.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import dev.rhcehd123.dailylog.R
import dev.rhcehd123.dailylog.ui.component.Loading
import dev.rhcehd123.dailylog.ui.model.ContentType
import dev.rhcehd123.dailylog.ui.screen.settings.component.ContentTypeSetting
import dev.rhcehd123.dailylog.ui.theme.DailyLogTheme

@Composable
fun SettingsDialog(
    viewModel: SettingsViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    SettingsDialog(
        uiState = uiState,
        onDismiss = onDismiss,
        onChangeContentType = viewModel::updateContentType
    )
}

@Composable
fun SettingsDialog(
    uiState: SettingsUiState,
    onDismiss: () -> Unit,
    onChangeContentType: (ContentType) -> Unit
) {
    val dialogProperties = remember {
        DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    }
    Dialog(
        onDismissRequest = onDismiss,
        properties = dialogProperties,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (uiState.isLoading) {
                Loading(modifier = Modifier.height(120.dp))
            } else {
                Column(
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 24.dp
                    )
                ) {
                    SettingsTitle()
                    SettingsOptions(
                        uiState = uiState,
                        onChangeContentType = onChangeContentType
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsTitle() {
    Text(
        text = stringResource(R.string.settings),
        fontSize = TextUnit(30f, TextUnitType.Sp)
    )
    Divider()
}

@Composable
fun SettingsOptions(
    uiState: SettingsUiState,
    onChangeContentType: (ContentType) -> Unit
) {
    Column {
        ContentTypeSetting(
            uiState = uiState,
            onChangeContentType = onChangeContentType
        )
        /*Text(
            text = stringResource(R.string.about)
        )*/
    }
}

@Composable
fun Divider() {
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 8.dp),
        thickness = 2.dp,
    )
}

@Composable
@Preview
fun SettingsDialogPreview() {
    DailyLogTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            SettingsDialog(
                uiState = SettingsUiState(),
                onDismiss = {},
                onChangeContentType = {}
            )
        }
    }
}

@Composable
@Preview
fun SettingsDialogLoadingPreview() {
    DailyLogTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            SettingsDialog(
                uiState = SettingsUiState(isLoading = true),
                onDismiss = {},
                onChangeContentType = {}
            )
        }
    }
}