package com.example.planner.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.planner.R
import com.example.planner.data.database.Task
import com.example.planner.ui.theme.PlannerTheme
import java.time.LocalDate


@Composable
fun ShowAddTaskDialog(
    taskUiState: TaskUiState,
    onDismissDialog: () -> Unit,
    onAddTask: (String, LocalDate) -> Unit,
    showDialog: Boolean
) {


    if (showDialog) {
        Dialog(onDismissRequest = onDismissDialog) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {

                    var text by remember { mutableStateOf("") }
                    val focusRequester = remember { FocusRequester() }

                    Text(text = "Add Task", fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        label = { Text("Task") },
                        singleLine = true,
                        textStyle = TextStyle(
                            color = colorResource(R.color.purple_200),
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .focusRequester(focusRequester)
                            .padding(16.dp)

                    )

                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()

                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {


                        Button(onClick = onDismissDialog, modifier = Modifier.weight(1f)) {
                            Text(text = "Cancel", textAlign = TextAlign.Center)
                        }

                        Button(onClick = {

                            onAddTask(text, taskUiState.selectedDate)
                            onDismissDialog()

                        }, modifier = Modifier.weight(1f)) {
                            Text(text = "Add", textAlign = TextAlign.Center)
                        }

                    }

                }
            }
        }

    }

}


@Composable
fun ShowTaskDialog(
    dialogTitle:String,
    task:Task,
    onDismissDialog: () -> Unit,
    onSaveTask:  (task:Task)->Unit,
    showDialog: Boolean
) {


    if (showDialog) {

        var isError by remember { mutableStateOf(false) }
        val titleErrorText= stringResource(R.string.title_is_empty)
        fun validateText(text:String){
            isError=text.isEmpty()
        }
        Dialog(onDismissRequest = onDismissDialog) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(16.dp),
                shape = MaterialTheme.shapes.large//RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {

                    var textFieldValue by remember { mutableStateOf(TextFieldValue(text=task.title, selection = TextRange(task.title.length))) }
                   // var text by remember { mutableStateOf("") }
                    val focusRequester = remember { FocusRequester() }

                    Text(text = dialogTitle, style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(
                        value = textFieldValue,
                        onValueChange = { textFieldValue = it
                                        // task.title=it.text
                                         validateText(it.text)},
                        isError = isError,
                        supportingText = {
                            if(isError){
                                Text(text=titleErrorText,
                                    modifier = Modifier.fillMaxWidth().align(alignment = Alignment.Start),
                                    color=MaterialTheme.colorScheme.error
                                    )
                            }
                        },
                        label = { Text(stringResource(R.string.task)) },
                        singleLine = true,
                        textStyle = TextStyle(
                           // color = colorResource(R.color.purple_200),
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier
                            .focusRequester(focusRequester)
                            .padding(16.dp),


                    )

                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()

                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {


                        Button(onClick = onDismissDialog, modifier = Modifier.weight(1f)) {
                            Text(text = stringResource(R.string.cancel), textAlign = TextAlign.Center)
                        }

                        Button(onClick = {

                            if(textFieldValue.text.isEmpty()){
                                isError=true
                            }else {
                                task.title=textFieldValue.text
                                onSaveTask(task)
                                onDismissDialog()
                            }

                        }, modifier = Modifier.weight(1f)) {
                            Text(text = stringResource(R.string.save), textAlign = TextAlign.Center)
                        }

                    }

                }
            }
        }


    }

}


/*
@Composable
fun ShowUpdateTaskDialog(
    task: Task,
    onDismissDialog: () -> Unit,
    onUpdateTask: (task: Task) -> Unit,
    showDialog: Boolean
) {


    if (showDialog) {
        Dialog(onDismissRequest = onDismissDialog) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {

                    var textFieldValue by remember { mutableStateOf(TextFieldValue(text=task.title, selection = TextRange(task.title.length))) }
                    val focusRequester = remember { FocusRequester() }

                    Text(text = "Edit Task", fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = textFieldValue,
                        onValueChange = {
                            textFieldValue = it
                            task.title = it.text
                        },
                        label = { Text("Task") },
                        singleLine = true,
                        textStyle = TextStyle(
                            color = colorResource(R.color.purple_200),
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .focusRequester(focusRequester)
                            .padding(16.dp)

                    )

                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()

                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {


                        Button(onClick = onDismissDialog, modifier = Modifier.weight(1f)) {
                            Text(text = "Cancel", textAlign = TextAlign.Center)
                        }

                        Button(onClick = {

                            onUpdateTask(task)
                            onDismissDialog()

                        }, modifier = Modifier.weight(1f)) {
                            Text(text = "Save", textAlign = TextAlign.Center)
                        }

                    }

                }
            }
        }

    }

}*/


@Composable
fun ShowDeleteTaskDialog(
    task: Task,
    onDismissDialog: () -> Unit,
    onDeleteTask: (task: Task) -> Unit,
    showDialog: Boolean
) {

    if (showDialog) {
        Dialog(onDismissRequest = onDismissDialog) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                shape =MaterialTheme.shapes.large //RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    Text(text = "Delete Task", style = MaterialTheme.typography.titleMedium)

                    Text(
                        text = "Are you sure you want to Delete the task?",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {


                        Button(onClick = onDismissDialog, modifier = Modifier.weight(1f)) {
                            Text(text = "Cancel", textAlign = TextAlign.Center)
                        }

                        Button(onClick = {

                            onDeleteTask(task)
                            onDismissDialog()

                        }, modifier = Modifier.weight(1f)) {
                            Text(text = "OK", textAlign = TextAlign.Center)
                        }

                    }

                }
            }
        }

    }

}


@Preview
@Composable
private fun ShowAddTaskDialogPreview() {
    PlannerTheme {
        //ShowAddTaskDialog(onDismissDialog = {  }, onAddTask = {}, showDialog = true)
    }
}
