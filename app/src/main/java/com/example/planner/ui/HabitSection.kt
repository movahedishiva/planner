package com.example.planner.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.planner.R

@Composable
fun HabitSection() {

    val context = LocalContext.current
    Card(
        colors= CardDefaults.cardColors(containerColor = colorResource(R.color.purple_50)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column( modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)) {

            HabitTitle()
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(getHabitList()) { item ->
                    MyHabitItem(item, onCLicked ={ openHabit(item, context) })

                }
            }
        }
    }


}

fun openHabit(habit: Habit, context: Context) {

    Toast.makeText(context, habit.name, Toast.LENGTH_SHORT).show()
   //start new fragment or activity
}


@Composable
fun HabitTitle(modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Filled.Face,
            contentDescription = "habitSection",
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Text(text = stringResource(R.string.habit), modifier = Modifier, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun MyHabitItem(habit: Habit, onCLicked: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onCLicked),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(habit.icon), contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .border(2.dp, colorResource(R.color.purple_200), CircleShape),
            contentScale = ContentScale.Crop
        )
        Text(text = habit.name, modifier = Modifier.paddingFromBaseline(top = 8.dp))
    }
}

fun getHabitList(): List<Habit> {

    return listOf(
        Habit("Water", R.drawable.ic_launcher_background),
        Habit("Exercise", R.drawable.ic_launcher_background),
        Habit("Fruit", R.drawable.ic_launcher_background),
        Habit("Reading", R.drawable.ic_launcher_background),
        //Habit("Add", Icons.Filled.Add),
    )
}

data class Habit(val name: String, val icon: Int)

@Preview
@Composable
private fun HabitPreview() {
    HabitSection()
}