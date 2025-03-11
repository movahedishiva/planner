package com.example.planner.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Composable
fun CreateTaskScreen(
    screenTitle:String,
    task: Task,
    onDismissDialog: () -> Unit,
    onSaveTask:  (task: Task)->Unit,
    ) {




        var isError by remember { mutableStateOf(false) }
        val titleErrorText= stringResource(R.string.title_is_empty)
        fun validateText(text:String){
            isError=text.isEmpty()
        }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight().padding(16.dp)
                    ,
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {

                    var textFieldValue by remember { mutableStateOf(TextFieldValue(text=task.title, selection = TextRange(task.title.length))) }
                    val focusRequester = remember { FocusRequester() }

                    Text(text = screenTitle, style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(
                        value = textFieldValue,
                        onValueChange = { textFieldValue = it
                            task.title=it.text
                            validateText(task.title)},
                        isError = isError,
                        supportingText = {
                            if(isError){
                                Text(text=titleErrorText,
                                    modifier = Modifier.fillMaxWidth().align(alignment = Alignment.Start),
                                    color= MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        label = { Text(stringResource(R.string.title)) },
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

                            if(task.title.isEmpty()){
                                isError=true
                            }else {
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

@SuppressLint("ComposableNaming")
@Preview()
@Composable
fun CreateTaskPreview(){
    CreateTaskScreen("addTask",Task(1,"task1",""), {},{})
}