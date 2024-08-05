package com.vishu.calculatorcompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CalculatorApp() {
    val displayText = remember { mutableStateOf("0") }
    val currentInput = remember { mutableStateOf("") }
    val storedValue = remember { mutableStateOf("") }
    val currentOperation = remember { mutableStateOf<String?>(null) }

    val onButtonClick: (String) -> Unit = { button ->
        when (button) {
            "AC" -> {
                currentInput.value = ""
                storedValue.value = ""
                currentOperation.value = null
                displayText.value = "0"
            }

            "+/-" -> {
                if (currentInput.value.isNotEmpty()) {
                    currentInput.value = if (currentInput.value.startsWith("-")) {
                        currentInput.value.substring(1)
                    } else {
                        "-${currentInput.value}"
                    }
                    displayText.value = currentInput.value
                }
            }

            "%" -> {
                if (currentInput.value.isNotEmpty()) {
                    currentInput.value = (currentInput.value.toDouble() / 100).toString()
                    displayText.value = currentInput.value
                }
            }

            "÷", "×", "-", "+" -> {
                if (currentInput.value.isNotEmpty()) {
                    storedValue.value = currentInput.value
                    currentOperation.value = button
                    currentInput.value = ""
                }
            }

            "=" -> {
                if (currentInput.value.isNotEmpty() && storedValue.value.isNotEmpty() && currentOperation.value != null) {
                    val result = when (currentOperation.value) {
                        "÷" -> storedValue.value.toDouble() / currentInput.value.toDouble()
                        "×" -> storedValue.value.toDouble() * currentInput.value.toDouble()
                        "-" -> storedValue.value.toDouble() - currentInput.value.toDouble()
                        "+" -> storedValue.value.toDouble() + currentInput.value.toDouble()
                        else -> 0.0
                    }
                    displayText.value = result.toString()
                    currentInput.value = result.toString()
                    storedValue.value = ""
                    currentOperation.value = null
                }
            }

            else -> {
                currentInput.value += button
                displayText.value = currentInput.value
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        BasicCalculatorLayout(displayText.value, onButtonClick)
    }
}