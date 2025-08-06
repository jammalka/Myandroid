package com.example.myapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapp.ui.theme.screens.dashboard.DashboardScreen
import com.example.myapp.ui.theme.screens.login.loginScreen
import com.example.myapp.ui.theme.screens.patient.AddPatientScreen
import com.example.myapp.ui.theme.screens.patient.UpdatePatientScreen
import com.example.myapp.ui.theme.screens.patient.viewPatientScreen
import com.example.myapp.ui.theme.screens.register.registerScreen

@Composable
fun AppNavHost(navController:NavHostController= rememberNavController(),startDestination:String= ROUTE_DASHBOARD) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(ROUTE_REGISTER) {
            registerScreen(navController)
        }
        composable(ROUTE_LOGIN)
        { loginScreen(navController) }
        composable(ROUTE_DASHBOARD)
        { DashboardScreen(navController) }
        composable(ROUTE_PATIENT)
        { AddPatientScreen(navController) }
        composable(ROUTE_VIEW_PATIENT)
        { viewPatientScreen((navController)) }
        composable(
            ROUTE_UPDATE_PATIENT,
            arguments = listOf(navArgument("patientId") { type = NavType.StringType })
        ) { backStackEntry ->
            val patientId = backStackEntry.arguments?.getString("patientId")!!
            UpdatePatientScreen(navController, patientId)

        }
    }
}