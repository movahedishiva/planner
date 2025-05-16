package com.shiva.planner

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.shiva.planner.util.getFormattedDate
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class MainActivityTest{

    @get:Rule
    val composeTestRule= createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController
    @Before
    fun setUpMainActivity(){
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
          //  Text("helooooo")
            MainScreen(navController=navController)
        }
    }

    @Test
    fun verify_StartDestination(){
         navController.assertCurrentRouteName(PlannerScreen.Start.name)
        //assertEquals(PlannerScreen.Start.name,navController.currentBackStackEntry?.destination?.route)
    }


    @Test
    fun onAddButton() {

        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.add)).performClick()
        navController.assertCurrentRouteName(PlannerScreen.CreateTask.name)
    }

    @Test
    fun show_currentDay_onSelectedDate(){
        val now =getFormattedDate(LocalDate.now())
        composeTestRule.onNodeWithTagStringId(R.string.selectedDate).assertTextEquals(now)
    }

    @Test
    fun show_selectedDay_onSelectedDate(){
        val now =LocalDate.now()
        val anotherDay=LocalDate.ofYearDay(now.year,now.dayOfYear+2)
        //println(getFormattedDate(anotherDay)+" test****")
        composeTestRule.onNodeWithText(anotherDay.dayOfMonth.toString()).performClick()
        composeTestRule.onNodeWithTagStringId(R.string.selectedDate).assertTextEquals(getFormattedDate(anotherDay))
    }

    @Test
    fun addTask(){
        onAddButton()

    }




}