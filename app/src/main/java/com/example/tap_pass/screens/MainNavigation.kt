package com.example.tap_pass.screens

import BiometricAuthScreen
import NfcViewModel
import android.app.Activity
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tap_pass.MainActivity
import com.example.tap_pass.R
import com.example.tap_pass.screens.loginscreen.LoginScreen
import com.example.tap_pass.screens.mainscreen.MainScreen
import com.example.tap_pass.screens.meetings.MeetingScreen
import com.example.tap_pass.screens.profileScreen.ProfileScreen

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun MainNavigation(nfcpresent: Boolean, nfcViewModel: NfcViewModel, mainActivity: MainActivity) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val entered = remember {
        mutableStateOf(false)
    }



    Scaffold(
        bottomBar = {
            if (currentRoute == Screen.MainScreen.name || currentRoute == Screen.Profile.name) {
                BottomNavigationBar(navController, currentRoute)
            }
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            NavHost(navController = navController, startDestination = Screen.Biometric.name) {
                composable(route = Screen.Biometric.name) {
                    BiometricAuthScreen(navController)
                }

                composable(route = Screen.Login.name) {
                    CustomBackHandler(navController)
                    LoginScreen(navController)
                }

                composable(route = Screen.MainScreen.name) {
                    CustomBackHandler(navController)
                    MainScreen(navController, nfcpresent, nfcViewModel, mainActivity,entered)
                }

                composable(route = Screen.Profile.name) {
                    ProfileScreen(navController)
                }
                composable(route = Screen.Meetings.name) {
                    MeetingScreen()
                }
                composable(route = Screen.CoffeeMachine.name) {
                   CoffeeMachine(navController)
                }
                composable(route = Screen.Exit.name){
                    Exit(navController = navController)
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, currentRoute: String?) {
    NavigationBar(
        modifier = Modifier.height(100.dp), containerColor = Color.Transparent
    ) {
        val navigateTo = { route: String ->
            if (currentRoute != route) {
                navController.navigate(route) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }

        NavigationBarItem(
            selected = currentRoute == Screen.MainScreen.name,
            onClick = { navigateTo(Screen.MainScreen.name) },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.nfcblack),
                    contentDescription = "Home",
                    modifier = Modifier.size(29.dp)
                )
            },
            colors = NavigationBarItemDefaults.colors(
            )
        )
        NavigationBarItem(
            selected = currentRoute == Screen.Profile.name,
            onClick = { navigateTo(Screen.Profile.name) },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.user__1_),
                    contentDescription = "Home",
                    modifier = Modifier.size(26.dp)
                )
            }
        )
    }
}

@Composable
fun CustomBackHandler(navController: androidx.navigation.NavController) {
    val context = LocalContext.current
    BackHandler {
        (context as? Activity)?.finish()
    }
}

enum class Screen {
    Biometric,
    Login,
    MainScreen,
    Profile,
    Meetings,
    CoffeeMachine,
    Exit
}

