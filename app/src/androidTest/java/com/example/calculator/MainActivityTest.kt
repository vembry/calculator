package com.example.calculator

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun before() {
        composeTestRule.setContent {
            Calculator()
        }
    }

    @Test
    fun clickAllNumberButton() {
        // preps
        val numbers = listOf(9, 8, 7, 6, 5, 4, 3, 2, 1, 0)

        // execs
        for (number in numbers) {
            composeTestRule.onNodeWithTag("btn_calc_$number").performClick()
        }

        // asserts
        composeTestRule.onNodeWithTag("txt_calculator").assertTextEquals("9876543210")
    }


    @Test
    fun doCalculations() {
        /**
         * Calculation is a helper class so we just need to define test parameters
         * instead of having one function for each operations
         *
         * @property elements contain all buttons we want to press in array format
         * @property result contain the expected result of the calculation based on given elements
         */
        class Calculation(val elements: Array<String>, val result: String)

        // preps
        val operations = arrayOf(
            // simple calculation cases
            Calculation(
                elements = arrayOf("1", CALCULATION_OPERATOR_ADDITION, "2"),
                result = "3"),
            Calculation(
                elements = arrayOf("1", CALCULATION_OPERATOR_SUBTRACTION, "2"),
                result = "-1"),
            Calculation(
                elements = arrayOf("2", CALCULATION_OPERATOR_MULTIPLY, "2"),
                result = "4"),
            Calculation(
                elements = arrayOf("1", "0", CALCULATION_OPERATOR_DIVISION, "2"),
                result = "5"),
            Calculation(
                elements = arrayOf("1", "0", CALCULATION_OPERATOR_DIVISION, "2", "+/-"),
                result = "-5"),
            Calculation(
                elements = arrayOf(
                    "2",
                    CALCULATION_NEGATES,
                    CALCULATION_OPERATOR_MULTIPLY,
                    "2",
                    CALCULATION_NEGATES
                ),
                result = "4"
            ),
            Calculation(
                elements = arrayOf("2", CALCULATION_NEGATES, CALCULATION_OPERATOR_MULTIPLY, "2"),
                result = "-4"
            ),
            Calculation(
                elements =  arrayOf("2", CALCULATION_OPERATOR_MULTIPLY, "2", CALCULATION_NEGATES),
                result =  "-4"
            ),

            // tricky cases
            Calculation(
                elements = arrayOf("1", CALCULATION_NEGATES),
                result = "-1"
            ),
            Calculation(
                elements = arrayOf("1", CALCULATION_PERCENTAGE),
                result = "0.01"
            ),
            Calculation(
                elements = arrayOf("1", CALCULATION_CLEAR, "2", "0", "0", CALCULATION_PERCENTAGE),
                result = "2"
            ),
            Calculation(
                elements = arrayOf("1", CALCULATION_PERCENTAGE, CALCULATION_OPERATOR_MULTIPLY, "3", CALCULATION_PERCENTAGE),
                result = "3.0E-4"
            ),

            // decimal cases
            Calculation(
                elements = arrayOf("1","1",CALCULATION_DECIMAL_LABEL,"1",CALCULATION_DECIMAL_LABEL,"1",CALCULATION_DECIMAL_LABEL, "2", CALCULATION_DECIMAL_LABEL, "3"),
                result = "11.1123"
            ),
            Calculation(
                elements = arrayOf("1","1",CALCULATION_DECIMAL_LABEL),
                result = "11"
            ),
            Calculation(
                elements = arrayOf(
                    "1",
                    "1",
                    CALCULATION_DECIMAL_LABEL,
                    "3",
                    CALCULATION_OPERATOR_ADDITION,
                    "1",
                    CALCULATION_DECIMAL_LABEL,
                    "1"
                ),
                result = "12.4"
            ),
        )

        // execs
        for (operation in operations) {
            // clear out calculator textbox
            composeTestRule.onNodeWithTag("btn_calc_${CALCULATION_CLEAR}").performClick()

            // iteration to press buttons on the calculator
            for (element in operation.elements) {
                composeTestRule.onNodeWithTag("btn_calc_${element}").performClick()
            }

            // click result after iterating through all wanted buttons
            composeTestRule.onNodeWithTag("btn_calc_${CALCULATION_OPERATOR_RESULT}").performClick()

            // asserts
            try {
                composeTestRule.onNodeWithTag("txt_calculator").assertTextEquals(operation.result)
            } catch (e: AssertionError) {
                println("calculation failed. elements=${operation.elements.contentToString()}. expecting=${operation.result}")
                throw e
            }
        }
    }
}