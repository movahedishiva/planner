package com.example.planner.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planner.R
import com.example.planner.data.database.Task
import com.example.planner.util.getFormattedDate

@Composable
fun ToDoSection(
    taskUiState: TaskUiState,
    onRemoveTask: (task: Task) -> Unit,
    onUpdateTask: (task: Task) -> Unit,
    modifier: Modifier = Modifier
) {

    val taskList = taskUiState.taskList.collectAsState(listOf()).value
    Column() {

        Text(
            text = getFormattedDate(taskUiState.selectedDate.value),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp),
            textAlign = TextAlign.Center,
            color = colorResource(R.color.purple_300),
            fontWeight = FontWeight.Bold
        )

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(horizontal = 16.dp), contentAlignment = Alignment.Center
        ) {

            // if(taskUiState.value.taskList.count()==0){
            if (taskList.isEmpty()) {
                Text(
                    text = "Click \" + \" to add a new task",
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(top = 32.dp, bottom = 16.dp), verticalArrangement = Arrangement.Top
                ) {

                    ToDoTitle(modifier)

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(vertical = 8.dp)
                    ) {
                        items(taskList) { task ->
                            TaskCard(task, onRemoveTask = onRemoveTask, onUpdateTask = onUpdateTask)
                        }
                    }
                }
            }


        }
    }

}


@Composable
fun ToDoTitle(modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Filled.DateRange,
            contentDescription = "ToDOSection",
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Text(
            text = stringResource(R.string.taskToDo),
            modifier = Modifier,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun TaskCard(task: Task, onRemoveTask: (task: Task) -> Unit, onUpdateTask: (task: Task) -> Unit) {

    var checkBoxSelected by mutableStateOf(task.isCompleted)
    var showDeleteDialog by remember { mutableStateOf(false) }

    var showUpdateDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog)
        ShowDeleteTaskDialog(
            task,
            onDismissDialog = { showDeleteDialog = false },
            onDeleteTask = onRemoveTask,
            showDeleteDialog
        )
    if(showUpdateDialog){
        ShowTaskDialog(
            stringResource(R.string.editTask),
            task,
            onDismissDialog = {showUpdateDialog=false},
            onSaveTask =onUpdateTask,
            showUpdateDialog)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
       // colors = CardDefaults.cardColors(containerColor = colorResource(R.color.purple_50)),

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Checkbox(checked = checkBoxSelected, onCheckedChange = { isChecked ->
                task.isCompleted = isChecked
                checkBoxSelected = isChecked
                onUpdateTask(task)
            })
            Text(
                text = task.title, modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            )
            IconButton(onClick = { showDeleteDialog = true }) {
                Icon(Icons.Default.Delete, contentDescription = "close")
            }

            IconButton(onClick = { showUpdateDialog = true }) {
                Icon(Icons.Default.Edit, contentDescription = "edit")
            }
        }
    }
}




@Preview
@Composable
private fun ToDoSectionPreview() {

    // ToDoSection()
    // val task= Task(1,"title1","date","time")
    // TaskCard(task) {}
}

