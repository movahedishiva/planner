package com.shiva.planner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shiva.planner.ui.AppViewModelProvider
import com.shiva.planner.ui.TaskViewModel
import com.shiva.planner.ui.ToDoSection
import com.shiva.planner.ui.WeekCalendarSection
import com.shiva.planner.ui.theme.PlannerTheme

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shiva.planner.data.database.Task
import com.shiva.planner.ui.CreateTaskScreen
import com.shiva.planner.ui.TaskUiState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            PlannerTheme {
                Surface(
                    tonalElevation = 5.dp,
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
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
    taskViewModel: TaskViewModel= viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController = rememberNavController()
) {
    //val taskViewModel: TaskViewModel= viewModel(factory = AppViewModelProvider.Factory)
   // var showDialog by remember { mutableStateOf(false) }
    val taskUiState=taskViewModel.uiState.collectAsStateWithLifecycle()
    //val navController= rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen=backStackEntry?.destination?.route ?: PlannerScreen.Start.name

    Scaffold(modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->


            NavHost(navController =navController ,
                startDestination = PlannerScreen.Start.name,
                modifier = Modifier){
                composable(PlannerScreen.Start.name){
                    StartScreen(innerPadding,taskUiState,taskViewModel)

                }

                composable(PlannerScreen.CreateTask.name){
                   CreateTaskScreen( screenTitle = stringResource(R.string.addTask),task = Task(title = "",date=taskUiState.value.selectedDate.toString()),{navController.popBackStack(PlannerScreen.Start.name,inclusive = false)},onSaveTask = taskViewModel::addTask)
                }
            }
         //   ShowTaskDialog(dialogTitle = stringResource(R.string.addTask), task = Task(title = "",date=taskUiState.value.selectedDate.toString()),onDismissDialog = { showDialog = false },onSaveTask = taskViewModel::addTask, showDialog)


        },

            floatingActionButton = { if(currentScreen == PlannerScreen.Start.name)OnAddButton(onClick = { navController.navigate(PlannerScreen.CreateTask.name)/*showDialog = true*/ }) },
            floatingActionButtonPosition = FabPosition.Center

    )



}

@Composable
fun StartScreen(innerPadding:PaddingValues, taskUiState: State<TaskUiState>, taskViewModel: TaskViewModel){

    //val taskViewModel: TaskViewModel= viewModel(factory = AppViewModelProvider.Factory)

    Column(
        modifier = Modifier.padding( top = 16.dp,bottom = innerPadding.calculateBottomPadding())

    ) {

        WeekCalendarSection(taskUiState.value,taskViewModel::onDateSelected)
        //   HabitSection()
        ToDoSection(taskUiState.value,taskViewModel::deleteTask,taskViewModel::updateTask,taskViewModel::onFilterSelected)



    }


}

@Composable
fun OnAddButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
        shape = CircleShape ,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        Icon(Icons.Filled.Add, stringResource(R.string.add))
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    PlannerTheme {

        MainScreen()
       // val viewModel=TaskViewModel(PlannerApplication().container.taskRepository)
       // StartScreen(PaddingValues(10.dp), viewModel.uiState.collectAsStateWithLifecycle(),viewModel)

    }
}

