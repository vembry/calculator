package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.calculator.ui.theme.CalculatorTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                Calculator()
            }
        }
    }
}

/**
 * the basis of an element used on calculator app
 * @param value for labelling purposes
 * @param callback for calculation purposes
 */
class CalcElement(
    val value: String,
    val callback: () -> Unit,
)

/**
 * operator constants
 */
val operators = arrayOf("+", "-", "x", "/")

/**
 * to do calculation
 * @param operator accept operators
 * @param a denotes number to be calculated on the left side
 * @param b denotes number to be calculated on the right side
 */
fun calculate(operator: String, a: Double, b: Double): Double {
    return when (operator) {
        "+" -> a + b
        "-" -> a - b
        "x" -> a * b
        "/" -> a / b
        else -> 0.0
    }
}

/**
 * findResult is to calculate all element-entries made by the calculator
 * @param stack the list of element entries made by calculator app
 */
fun findResult(stack: MutableList<String>): Double {
    // when stack is empty, just break away
    if (stack.isEmpty()) {
        return 0.0
    }

    // check if stack's last entry is an operator
    val lastEntry = stack.last()
    if (operators.contains(lastEntry)) {
        // when yes, then get rid last entry
        stack.removeLast()
    }

    if (stack.size == 1) {
        // when stack contain only 1 item
        // then just add return it
        return stack.removeLast().toDouble()
    } else {
        var i = 0
        var count = stack[i++].toDouble()
        while (i < stack.size) {
            val operator = stack[i]
            val a = count;
            val b = stack[i + 1]

            count = calculate(operator, a, b.toDouble())
            i += 2
        }
        return count
    }
}

fun safeAddOperator(
    stack: MutableList<String>,
    operatorEntry: String,
    numberEntry: String
): MutableList<String> {
    if(numberEntry.isNotBlank()){
        stack.add(numberEntry)
    }
    if(stack.isNotEmpty()){
        stack.add(operatorEntry)
    }
    return stack
}

@Composable
fun Calculator() {
    // to assists UI
    var textBoxValue by remember { mutableStateOf("") }

    // contains all numbers + operators
    var stack by remember { mutableStateOf(mutableListOf<String>()) }

    // contains all calculator elements that can be added to 'stack'
    val elements = listOf(
        listOf(
            CalcElement("CE", callback = { textBoxValue = "" }),
            CalcElement("7", callback = { textBoxValue += "7" }),
            CalcElement("4", callback = { textBoxValue += "4" }),
            CalcElement("1", callback = { textBoxValue += "1" }),
            CalcElement("0", callback = { textBoxValue += "0" })
        ),
        listOf(
            CalcElement(
                "+/-",
                callback = { textBoxValue = (textBoxValue.toDouble() * -1).toString() }),
            CalcElement("8", callback = { textBoxValue += "8" }),
            CalcElement("5", callback = { textBoxValue += "5" }),
            CalcElement("2", callback = { textBoxValue += "2" }),
            CalcElement(",", callback = { textBoxValue += "." })
        ),
        listOf(
            CalcElement(
                "%",
                callback = { textBoxValue = (textBoxValue.toDouble() / 100).toString() }),
            CalcElement("9", callback = { textBoxValue += "9" }),
            CalcElement("6", callback = { textBoxValue += "6" }),
            CalcElement("3", callback = { textBoxValue += "3" })
        ),
        listOf(
            CalcElement("/", callback = {
                stack.add(textBoxValue)
                stack.add("/")
                textBoxValue = ""
            }),
            CalcElement("x", callback = {
                stack.add(textBoxValue)
                stack.add("x")
                textBoxValue = ""
            }),
            CalcElement("-", callback = {
                stack.add(textBoxValue)
                stack.add("-")
                textBoxValue = ""
            }),
            CalcElement("+", callback = {
                stack = safeAddOperator(stack, "+", textBoxValue)
                textBoxValue = ""
            }),
            CalcElement("=", callback = {
                if (textBoxValue.isNotBlank()) {
                    stack.add(textBoxValue)
                    textBoxValue = ""
                }

                val out = findResult(stack)
                textBoxValue = out.toString()
                if(textBoxValue.contains(".0")){
                    textBoxValue = textBoxValue.substring(0, textBoxValue.length-2)
                }

                stack.clear()
            }),
        )
    )

    // UI base
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // upper section of the calculator
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text("Stack contents: $stack")
                TextField(
                    value = textBoxValue,
                    onValueChange = {
                        textBoxValue = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }

        // lower section of the calculator
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            elements.forEach { elementY ->
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f) // Distribute available horizontal space evenly,
                ) {
                    elementY.forEach { elementX ->
                        Button(
                            onClick = { elementX.callback() },
                            modifier = Modifier
                                .fillMaxWidth() // Expand the button to fill the available width
                                .padding(4.dp), // Add padding around the button
                            shape = RoundedCornerShape(10)
                        ) {
                            Text(text = elementX.value)
                        }
                    }
                }
            }
        }
    }
}