package com.shiva.planner

import androidx.navigation.NavController
import org.junit.Assert


fun NavController.assertCurrentRouteName(expectedRouteName:String){
    Assert.assertEquals(expectedRouteName,currentBackStackEntry?.destination?.route)
}