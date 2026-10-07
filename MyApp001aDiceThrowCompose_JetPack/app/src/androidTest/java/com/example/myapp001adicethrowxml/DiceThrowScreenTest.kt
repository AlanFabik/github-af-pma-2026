package com.example.myapp001adicethrowxml

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class DiceThrowScreenTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun initialFaceIsOneAndRollButtonIsEnabled() {
        composeRule.onNodeWithText("Hod kostkou").assertExists()
        composeRule.onNodeWithContentDescription("Kostka: 1").assertExists()
        composeRule.onNodeWithText("Hodit").assertIsEnabled()
    }

    @Test
    fun buttonIsDisabledDuringRollAndResultIsValid() {
        val button = composeRule.onNodeWithText("Hodit")
        button.performClick()
        button.assertIsNotEnabled()
        composeRule.waitUntil(timeoutMillis = 10_000) {
            !button.fetchSemanticsNode().config.contains(SemanticsProperties.Disabled)
        }
        button.assertIsEnabled()
        val validFace = (1..6).any { value ->
            runCatching {
                composeRule.onNodeWithContentDescription("Kostka: $value").fetchSemanticsNode()
            }.isSuccess
        }
        check(validFace) { "Výsledek musí být od 1 do 6." }
    }
}
