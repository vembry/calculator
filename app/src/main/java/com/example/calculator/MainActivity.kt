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
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
                Calculator()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculatorTheme {
        Greeting("Android")
    }
}

@Composable
fun Calculator() {
    Row(
        modifier = Modifier.padding(8.dp)
    ) {
        Column {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "a")
            }
        }
        Column {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "b")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "c")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "d")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "e")
            }
        }
        Column {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "f")
            }
        }
    }
}