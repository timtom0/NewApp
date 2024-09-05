@file:OptIn(ExperimentalLayoutApi::class)

package com.example.newapp

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun TextFieldInfo(
    value: String,
    changeValue: (String) -> Unit,
    action: ImeAction,
    oneLine: Boolean = false,
    label: String,
    keyAction: () -> Unit = {},
    supText: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = changeValue,
        shape = RoundedCornerShape(16.dp),
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = oneLine,
        supportingText = supText,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = action),
        keyboardActions = KeyboardActions(onDone = { keyAction() })
    )

    val focusManager = LocalFocusManager.current
    val isKeyboardVisible = WindowInsets.isImeVisible

    LaunchedEffect(isKeyboardVisible) {
        delay(5L)
        if (!isKeyboardVisible) {
            focusManager.clearFocus()
        }
    }

}

@Composable
fun Cards(
    title: String,
    info: String,
    edit: () -> Unit,
    delete: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .animateContentSize()
            .padding(bottom = 10.dp)

    ) {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        title,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        maxLines = 1,
                        modifier = Modifier.weight(6f),
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Row(horizontalArrangement = Arrangement.End) {
                        IconButton(
                            onClick = edit,
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(Icons.Rounded.Create, null)
                        }

                        Spacer(modifier = Modifier.width(6.dp))
                        IconButton(
                            onClick = delete,
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(Icons.Rounded.Delete, null)
                        }
                    }
                }
                if (info.isNotEmpty()) Text(info)
            }
        }
    }
}