package com.example.planner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planner.ui.AppViewModelProvider
import com.example.planner.ui.ShowAddTaskDialog
import com.example.planner.ui.TaskViewModel
import com.example.planner.ui.ToDoSection
import com.example.planner.ui.WeekCalendarSection
import com.example.planner.ui.theme.PlannerTheme

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.planner.data.database.Task
import com.example.planner.ui.ShowTaskDialog
import com.example.planner.util.getFormattedCurrentTime
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            PlannerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                   // val viewModel: TaskViewModel = viewModel(factory = AppViewModelProvider.Factory)
                    MainScreen()
                }
            }

        }
    }


}

@Composable
fun MainScreen(
    taskViewModel: TaskViewModel= viewModel(factory = AppViewModelProvider.Factory)
) {

    var showDialog by remember { mutableStateOf(false) }
    val taskUiState=taskViewModel.uiState

    Scaffold(modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->

            Column(
                modifier = Modifier.padding( top = 16.dp,bottom = innerPadding.calculateBottomPadding())

            ) {

                WeekCalendarSection(taskUiState,taskViewModel::onDateSelected)
             //   HabitSection()
                ToDoSection(taskUiState,taskViewModel::deleteTask,taskViewModel::updateTask)


            }

            ShowTaskDialog(dialogTitle = stringResource(R.string.addTask), task = Task(title = "",date=taskUiState.selectedDate.value.toString()),onDismissDialog = { showDialog = false },onSaveTask = taskViewModel::addTask, showDialog)


        },

        floatingActionButton = { OnAddButton(onClick = { showDialog = true })},
        floatingActionButtonPosition= FabPosition.Center
    )



}


@Composable
fun OnAddButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
        shape = CircleShape
    ) {
        Icon(Icons.Filled.Add, "Add")
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    PlannerTheme {

      //  var showDialog by remember { mutableStateOf(false) }
        MainScreen()
     //   ShowAddTaskDialog(onDismissDialog = { showDialog = false },{}, showDialog)

    }
}

