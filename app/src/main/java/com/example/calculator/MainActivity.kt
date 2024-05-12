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
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculator.ui.theme.CalculatorTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import java.util.Stack


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

class CalcElement(
    val value: String,
    val callback: () -> Unit,
)

@Composable
fun Calculator() {
    var inputText by remember { mutableStateOf("") }
    val stack = Stack<String>()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                TextField(
                    value = inputText,
                    onValueChange = {
                        println("changed to $it")
                        inputText = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }


        val elements = listOf(
            listOf(
                CalcElement("CE", callback = { inputText = "" }),
                CalcElement("7", callback = { inputText += "7" }),
                CalcElement("4", callback = { inputText += "4" }),
                CalcElement("1", callback = { inputText += "1" }),
                CalcElement("0", callback = { inputText += "0" })
            ),
            listOf(
                CalcElement("+/-", callback= {}),
                CalcElement("8", callback= {inputText += "8"}),
                CalcElement("5", callback= {inputText += "5"}),
                CalcElement("2", callback= {inputText += "2"}),
                CalcElement(",", callback= {inputText += ","})
            ),
            listOf(
                CalcElement("%", callback = {inputText += "%"}),
                CalcElement("9", callback = {inputText += "9"}),
                CalcElement("6", callback = {inputText += "6"}),
                CalcElement("3", callback = {inputText += "3"})
            ),
            listOf(
                CalcElement("/", callback = {
                    stack.add(inputText)
                    stack.add("/")
                    inputText = ""
                }),
                CalcElement("x", callback = {
                    stack.add(inputText)
                    stack.add("x")
                    inputText = ""
                }),
                CalcElement("-", callback = {
                    stack.add(inputText)
                    stack.add("-")
                    inputText = ""
                }),
                CalcElement("+", callback = {
                    stack.add(inputText)
                    stack.add("+")
                    inputText = ""
                }),
                CalcElement("=", callback = {
                    inputText += "="
                }),
            )
        )

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