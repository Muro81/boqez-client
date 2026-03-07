package com.lmuro.boqez.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.lmuro.boqez.theme.BoqezThemeProvider

@Composable
fun BoqezTextField(
    value: String,
    label: String = "",
    placeHolder: String,
    errorMessage: String = "",
    isPassword : Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onValueChange: (String) -> Unit
) {
    val isError = errorMessage.isNotEmpty()
    var passwordVisible by remember { mutableStateOf(false) }

    val visualTransformation = when {
        isPassword && !passwordVisible -> PasswordVisualTransformation()
        else -> VisualTransformation.None
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ){
        if(value.isNotEmpty())
        Text(
            text = label,
            color = BoqezThemeProvider.colors.inkBase,
            style = BoqezThemeProvider.typography.interRegular14
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(
                    color = BoqezThemeProvider.colors.white,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    1.dp,
                    if (isError) BoqezThemeProvider.colors.redBase else BoqezThemeProvider.colors.skyLighter,
                    RoundedCornerShape(8.dp)
                ),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            textStyle = BoqezThemeProvider.typography.interRegular14,
            singleLine = true,
            visualTransformation = visualTransformation,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeHolder,
                            color = BoqezThemeProvider.colors.inkBase,
                            style = BoqezThemeProvider.typography.interRegular14,
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
                    if (isPassword) {
                        IconButton(
                            onClick = { passwordVisible = !passwordVisible },
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = null,
                                tint = BoqezThemeProvider.colors.inkBase
                            )
                        }
                    }
                }
            },
        )
        if(errorMessage.isNotEmpty())
        Text(
            text = errorMessage,
            color = BoqezThemeProvider.colors.redBase,
            style = BoqezThemeProvider.typography.interRegular14
        )
    }
}