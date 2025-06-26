package dev.rhcehd123.dailylog

import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.isSelectable
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.rhcehd123.dailylog.ui.screen.settings.SettingsDialog
import dev.rhcehd123.dailylog.utils.TestTag
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SettingsDialogTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Test
    fun contentTypeSetting_selectEachOption_displaysCorrect() {
        composeTestRule.setContent {
            SettingsDialog(onDismiss = {})
        }
        val options = listOf("1", "2")
        for (option in options) {
            composeTestRule.onNodeWithTag("${TestTag.ContentTypeSetting}_$option")
                .performClick()
                .onChildren()
                .filterToOne(isSelectable())
                .assertIsSelected()
        }
    }
}