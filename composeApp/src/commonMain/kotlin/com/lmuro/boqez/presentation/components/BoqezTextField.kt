package com.lmuro.boqez.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BoqezTextField(
    value: String,
    label: String = "",
    placeHolder: String,
    errorMessage: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onValueChange: (String) -> Unit
) {
    val isError = errorMessage.isNotEmpty()
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, if (isError) Color.Red else Color.Gray, RoundedCornerShape(8.dp)),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
//        textStyle = ,
        singleLine = true,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = placeHolder,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterStart),
                    contentAlignment = Alignment.CenterStart
                ) {
                    innerTextField()
                }
            }
        },
    )
}