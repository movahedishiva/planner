package com.example.planner.ui

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.planner.R
import com.example.planner.data.database.Task
import com.example.planner.util.ShareBitmapFromComposable
import com.example.planner.util.getFormattedDate
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ToDoSection(
    taskUiState: TaskUiState,
    onRemoveTask: (task: Task) -> Unit,
    onUpdateTask: (task: Task) -> Unit,
    onClickFilter: (taskType: TaskType) -> Unit,
    modifier: Modifier = Modifier
) {

    val taskList = taskUiState.taskList.collectAsStateWithLifecycle(listOf()).value


    val context = LocalContext.current
    // val coroutineScope = rememberCoroutineScope()
    // val snackbarHostState = remember { SnackbarHostState() }
  //  val graphicsLayer = rememberGraphicsLayer()

  /*  val writeStorageAccessState = rememberMultiplePermissionsState(
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // No permissions are needed on Android 10+ to add files in the shared storage
            emptyList()
        } else {
            listOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    )*/

    Column() {

        Text(
            text = getFormattedDate(taskUiState.selectedDate),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp).testTag(stringResource(R.string.selectedDate)),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        Box(
            modifier = Modifier
               /* .drawWithCache {
                    onDrawWithContent {
                        graphicsLayer.record {
                            this@onDrawWithContent.drawContent()
                        }
                        drawLayer(graphicsLayer)
                    }
                }*/
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(horizontal = 16.dp), contentAlignment = Alignment.Center

        ) {

            // if(taskUiState.value.taskList.count()==0){
            if (taskList.isEmpty() && taskUiState.taskType.equals(TaskType.All)) {
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

                    //  val a= @Composable {context:Context,graphicsLayer:GraphicsLayer -> shareBitmapFromComposable(context,graphicsLayer) }
                    // val a: @Composable () -> Unit = { shareBitmapFromComposable(context,graphicsLayer,writeStorageAccessState) }
                  //  var callShare by remember { mutableStateOf(false) }
                    ToDoTitle(modifier, taskUiState, /*{ callShare = true },*/ onClickFilter)
                  /*  if (callShare) {
                        ShareBitmapFromComposable(
                            context,
                            graphicsLayer,
                            writeStorageAccessState,
                            { callShare = false })
                        *//* SideEffect {
                             if(callShare)
                             callShare = false
                         }*//*
                    }*/

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight()
                            , contentPadding = PaddingValues(8.dp)
                    ) {

                        items(taskList) { task ->
                            TaskCard(
                                task,
                                onRemoveTask = onRemoveTask,
                                onUpdateTask = onUpdateTask,
                              //  callShare,
                                //graphicsLayer
                            )
                        }
                    }
                }
                // }

            }


        }
    }

}


@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ToDoTitle(
    modifier: Modifier = Modifier,
    taskUiState: TaskUiState,
    //onClickShare: () -> Unit,
    onClickFilter: (taskType: TaskType) -> Unit
) {

    var showFilterDialog by remember { mutableStateOf(false) }
    if (showFilterDialog)
        FilterDialog(taskUiState, { showFilterDialog = false }, onClickFilter)

    Column {

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "ToDOSection",
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = stringResource(R.string.taskToDo),
                modifier = Modifier,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.weight(1f))

            //TODO: share
            /* IconButton(onClick = onClickShare) {
            Icon(Icons.Default.Share, contentDescription = "Share")
        }*/

            IconButton(onClick = { showFilterDialog = true }) {
                Icon(Icons.Default.Menu, contentDescription = "filter")
            }
        }
        HorizontalDivider(thickness = 1.dp, color = Color.Gray)

    }
}


@Composable
fun FilterDialog(
    taskUiState: TaskUiState,
    onDismissDialog: () -> Unit,
    onClickFilter: (taskType: TaskType) -> Unit
) {


    Dialog(onDismissRequest = onDismissDialog) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.large//RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {


                Text(
                    text = stringResource(R.string.filter),
                    style = MaterialTheme.typography.titleMedium
                )

                val radioOptions = listOf(TaskType.All, TaskType.COMPLETED, TaskType.INCOMPLETE)
                val (selectedOption, onOptionSelected) = remember { mutableStateOf(taskUiState.taskType) }
                Column(Modifier.selectableGroup()) {
                    radioOptions.forEach { text ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .padding(horizontal = 16.dp)
                                .selectable(
                                    selected = (text.equals(selectedOption)),
                                    onClick = {
                                        onOptionSelected(text)
                                    },
                                    role = Role.RadioButton
                                ), verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (text == selectedOption),
                                onClick = null
                            )
                            Text(
                                text = text.name,
                                modifier = Modifier.padding(start = 16.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {


                    Button(onClick = onDismissDialog, modifier = Modifier.weight(1f)) {
                        Text(text = stringResource(R.string.cancel), textAlign = TextAlign.Center)
                    }

                    Button(onClick = {

                      //  taskList=  taskUiState.taskList.map{it.filter { task -> task.isCompleted == true }}
                        onClickFilter(selectedOption)
                        onDismissDialog()

                    }, modifier = Modifier.weight(1f)) {
                        Text(text = stringResource(R.string.filter), textAlign = TextAlign.Center)
                    }

                }

            }
        }
    }


}


@SuppressLint("UnrememberedMutableState")
@Composable
fun TaskCard(
    task: Task,
    onRemoveTask: (task: Task) -> Unit,
    onUpdateTask: (task: Task) -> Unit,
    //hideActions: Boolean,
    //graphicsLayer: GraphicsLayer
) {

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
    if (showUpdateDialog) {
        ShowTaskDialog(
            stringResource(R.string.editTask),
            task,
            onDismissDialog = { showUpdateDialog = false },
            onSaveTask = onUpdateTask,
            showUpdateDialog
        )
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
            //  if(!showActions) {
            //  graphicsLayer.
            IconButton(/*modifier = Modifier.graphicsLayer {
                if (hideActions) alpha = 0f else alpha = 1f
            },*/ onClick = { showDeleteDialog = true }) {
                Icon(Icons.Default.Delete, contentDescription = "delete")
            }

            IconButton(onClick = { showUpdateDialog = true }) {
                Icon(Icons.Default.Edit, contentDescription = "edit")
            }
            //  }
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

