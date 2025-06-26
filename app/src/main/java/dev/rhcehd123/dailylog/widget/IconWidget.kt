package dev.rhcehd123.dailylog.widget

import android.content.Context
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Box
import androidx.glance.layout.size
import androidx.glance.layout.wrapContentSize
import dev.rhcehd123.dailylog.MainActivity
import dev.rhcehd123.dailylog.R

class IconWidget: GlanceAppWidget() {
    /*override val sizeMode: SizeMode = SizeMode.Responsive(
        setOf(
            DpSize(100.dp, 100.dp),
            DpSize(200.dp, 200.dp),
            DpSize(300.dp, 300.dp),
        )
    )*/
    override val sizeMode = SizeMode.Single
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Box(
                modifier = GlanceModifier
                    .wrapContentSize()
                    //.size(48.dp)
                    .clickable(actionStartActivity<MainActivity>())
            ) {
                Image(
                    provider = ImageProvider(R.drawable.ic_launcher_foreground),
                    contentDescription = "아이콘 버튼",
                    modifier = GlanceModifier.size(48.dp)
                )
            }
        }
    }
}