@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package com.example.newapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newapp.data.CardItem
import com.example.newapp.ui.CardViewModel
import com.example.newapp.ui.theme.NewAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        WindowCompat.setDecorFitsSystemWindows(window, false)

        window.setNavigationBarContrastEnforced(false)

        setContent {
            NewAppTheme {
                AppPreview()
            }
        }
    }
}



@Composable
fun TextFieldInfo(
    value: String,
    changeValue: (String) -> Unit,
    action: ImeAction,
    oneLine: Boolean,
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


@Composable
fun App(cardViewModel: CardViewModel = viewModel()) {
    var title by remember { mutableStateOf("") }
    var info by remember { mutableStateOf("") }
    var editIndex by remember { mutableIntStateOf(-1) }
    val cardList by cardViewModel.uiState.collectAsState()
    var showInput by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(modifier = Modifier.imePadding(), onClick = { showInput = true })
            {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Icon(
                        Icons.Rounded.Add,
                        null,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Add")
                }

            }
        }, topBar = {
            TopAppBar({
                CenterAlignedTopAppBar(title = {
                    Text(
                        "${cardList.size} Cards",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                })
            })
        }) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(start = 10.dp, end = 10.dp)
            ) {
                itemsIndexed(cardList) { index, card ->
                    Cards(card.title, card.info, {
                        title = card.title
                        info = card.info
                        editIndex = index
                        showInput = true
                    }, { cardViewModel.delete(card) })
                }
            }
            if (showInput) {
                ModalBottomSheet(
                    shape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp),
                    dragHandle = { },
                    onDismissRequest = { showInput = false },
                    sheetState = sheetState,
                    windowInsets = WindowInsets.displayCutout,
                ) {
                    val bottomPadding =
                        WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

                    Column(
                        modifier = Modifier
                            .imePadding()
                            .padding(
                                bottom = bottomPadding + 14.dp,
                                top = 16.dp,
                                start = 16.dp,
                                end = 16.dp
                            )
                    ) {

                        TextFieldInfo(
                            value = title,
                            { title = it },
                            ImeAction.Next,
                            true,
                            "Title", supText =
                            { if (info.isNotEmpty() && title.isEmpty()) {
                                Text("The card must has a title") }
                            }
                        )
                        if (title.isEmpty() && info.isNotEmpty()) Spacer(
                            modifier = Modifier.height(
                                8.dp
                            )
                        )

                        TextFieldInfo(
                            value = info,
                            { info = it },
                            if (title.isEmpty()) ImeAction.Previous else ImeAction.Done,
                            false,
                            "Info",
                            {
                                if (editIndex == -1) {
                                    cardViewModel.add(CardItem(cardList.size + 1, title, info))
                                    title = ""
                                    info = ""
                                } else {
                                    cardViewModel.edit(CardItem(editIndex + 1 , title, info))
                                    title = ""
                                    info = ""
                                }
                                scope.launch {
                                    sheetState.hide()
                                    showInput = false
                                    editIndex = -1}
                            }
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextButton(onClick = { showInput = false }) {
                                Text(text = "Close")
                            }

                            if (editIndex == -1) {
                                Button(enabled = title.isNotEmpty(), onClick = {
                                    cardViewModel.add(CardItem(cardList.size + 1, title, info))
                                    title = ""
                                    info = ""
                                    scope.launch {
                                        sheetState.hide()
                                        showInput = false }
                                }) {
                                    Text(text = "Save")
                                }
                            } else {
                                Button(enabled = title.isNotEmpty(), onClick = {
                                    cardViewModel.edit(CardItem(editIndex + 1, title, info))
                                    title = ""
                                    info = ""
                                    scope.launch {
                                        sheetState.hide()
                                        showInput = false
                                        editIndex = -1
                                    }
                                }) {
                                    Text(text = "Edit")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun AppPreview() {
    App()
}