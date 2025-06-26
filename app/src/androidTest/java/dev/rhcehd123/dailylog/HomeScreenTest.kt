package dev.rhcehd123.dailylog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.rhcehd123.dailylog.ui.screen.home.HomeScreen
import dev.rhcehd123.dailylog.utils.TestTag
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeScreenTest {
    @get:Rule(order = 0)
    val hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Before
    fun setup() {
        hiltAndroidRule.inject()
    }

    @Test
    fun addContentButton_isDisplayed() {
        composeTestRule.setContent {
            var fabSlot: @Composable () -> Unit by remember { mutableStateOf({}) }
            DailyLogScaffold(
                fabSlot = fabSlot
            ) {
                HomeScreen(
                    onNavigateToAddContent = {},
                    onChangeFabSlot = {
                        fabSlot = it
                    }
                )
            }
        }

        composeTestRule.onNodeWithTag(TestTag.AddContentButton)
            .assertIsDisplayed()
    }
}