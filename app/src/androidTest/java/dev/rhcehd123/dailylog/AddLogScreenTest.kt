package dev.rhcehd123.dailylog

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dev.rhcehd123.dailylog.ui.screen.addlog.AddLogScreen
import dev.rhcehd123.dailylog.ui.screen.addlog.AddLogViewModel
import dev.rhcehd123.dailylog.utils.TestTag
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class AddLogScreenTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Test
    fun commentField_updates_onTextInput() {
        val testComment = "test comment"
        composeTestRule.setContent {
            AddLogScreen(
                taskId = 0L,
                onNavigateToHome = {},
                onShowSnackbar = {},
                onChangeFabSlot = {}
            )
        }
        composeTestRule.onNodeWithTag(TestTag.CommentField)
            .performTextInput(testComment)

        composeTestRule.onNodeWithTag(TestTag.CommentField)
            .assertTextEquals(testComment)
    }

    @Test
    fun commentCount_updates_onCommentFieldChange() {
        val testComment = "test comment"
        composeTestRule.setContent {
            AddLogScreen(
                taskId = 0L,
                onNavigateToHome = {},
                onShowSnackbar = {},
                onChangeFabSlot = {}
            )
        }
        composeTestRule.onNodeWithTag(TestTag.CommentField)
            .performTextInput(testComment)
        composeTestRule.onNodeWithTag(TestTag.CommentCount)
            .assertTextEquals("${testComment.length}/${AddLogViewModel.MAX_COMMENT_LENGTH}")
    }

    @Test
    fun datePicker_shows_onDateFieldClick() {
        composeTestRule.setContent {
            AddLogScreen(
                taskId = 0L,
                onNavigateToHome = {},
                onShowSnackbar = {},
                onChangeFabSlot = {}
            )
        }

        composeTestRule.onNodeWithTag(TestTag.DateField)
            .performClick()

        composeTestRule.onNodeWithTag(TestTag.DatePicker)
            .isDisplayed()
    }

    @Test
    fun closeButton_isDisplayed() {
        composeTestRule.setContent {
            AddLogScreen(
                taskId = 0L,
                onNavigateToHome = {},
                onShowSnackbar = {},
                onChangeFabSlot = {}
            )
        }
        composeTestRule.onNodeWithTag(TestTag.CloseButton)
            .assertIsDisplayed()
    }

    @Test
    fun addButton_isDisplayed() {
        composeTestRule.setContent {
            AddLogScreen(
                taskId = 0L,
                onNavigateToHome = {},
                onShowSnackbar = {},
                onChangeFabSlot = {}
            )
        }
        composeTestRule.onNodeWithTag(TestTag.AddButton)
            .assertIsDisplayed()
    }
}
