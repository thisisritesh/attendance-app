package com.kaviriteshgupta.attendanceapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.kaviriteshgupta.attendanceapp.ui.ui.routes.AppRoutes
import com.kaviriteshgupta.attendanceapp.ui.ui.screens.LoginScreen
import com.kaviriteshgupta.attendanceapp.ui.ui.screens.MainScreen
import com.kaviriteshgupta.attendanceapp.ui.ui.theme.AttendanceAppTheme
import com.kaviriteshgupta.attendanceapp.ui.ui.viewmodels.AttendanceViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val attendanceViewModel: AttendanceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AttendanceAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavHost(innerPadding, attendanceViewModel)
                }
            }
        }

    }


    @Composable
    fun AppNavHost(innerPadding: PaddingValues, attendanceViewModel: AttendanceViewModel) {

        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = getDefaultRoute()) {
            composable(AppRoutes.HOME) {
                MainScreen(
                    modifier = Modifier.padding(innerPadding),
                    attendanceViewModel,
                    this@MainActivity
                ) {
                    navController.navigate(AppRoutes.LOGIN)
                }
            }
            composable(AppRoutes.LOGIN) {
                LoginScreen(onLoginClick = { email, password ->
                    attendanceViewModel.login(email, password) { isLoggedIn, errorMessage ->
                        if (isLoggedIn) {
                            navController.navigate(AppRoutes.HOME)
                        }
                    }
                })
            }
        }

    }

    private fun getDefaultRoute(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        return if (currentUser != null) {
            AppRoutes.HOME
        } else {
            AppRoutes.LOGIN
        }
    }

}

