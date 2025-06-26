package dev.rhcehd123.dailylog.ui.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import dev.rhcehd123.dailylog.ui.theme.RoundedCornerSize

@Composable
fun DetailCard(
    offset: Offset = Offset.Zero,
    onDismiss: () -> Unit = {},
    text: String = ""
) {
    Popup(
        onDismissRequest = onDismiss,
        offset = IntOffset(offset.x.toInt(), offset.y.toInt())
    ) {
        Box(
            modifier = Modifier
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(RoundedCornerSize.default)
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(RoundedCornerSize.default)
                )
        ) {
            Text(
                modifier = Modifier
                    .requiredWidthIn(
                        min = 100.dp,
                        max = 300.dp
                    )
                    .padding(
                        horizontal = 32.dp,
                        vertical = 16.dp
                    ),
                text = text,
            )
            IconButton(
                modifier = Modifier
                    .size(20.dp, 20.dp)
                    .align(Alignment.TopEnd),
                onClick = onDismiss,
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close"
                )
            }
        }
    }
}