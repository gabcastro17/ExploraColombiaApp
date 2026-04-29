package com.gabrielacastro.exploracolombiaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gabrielacastro.exploracolombiaapp.ui.elements.AddPlaceScreen
import com.gabrielacastro.exploracolombiaapp.ui.elements.HomeScreen
import com.gabrielacastro.exploracolombiaapp.ui.elements.LoginScreen
import com.gabrielacastro.exploracolombiaapp.ui.elements.RegisterScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val myNavController = rememberNavController()
            var myStartDestination: String = "login"

            val auth = Firebase.auth
            val currentUser = auth.currentUser

            if (currentUser != null) {
                myStartDestination = "home"
            } else {
                myStartDestination = "login "
            }

            NavHost(
                navController = myNavController,
                startDestination = "login",
                modifier = Modifier.fillMaxSize()
            ) {
                composable(route = "login") {
                    LoginScreen(
                        onNavigateToRegister = { myNavController.navigate("register") },
                        onLoginSuccess = {
                            myNavController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        })
                }
                composable(route = "register") {
                    RegisterScreen(onRegisterSuccess = {
                        myNavController.navigate("home") {
                            popUpTo(0)
                        }
                    }, onNavigateToLogin = {})
                }
                composable("home") {
                    HomeScreen(
                        onNavigateToAddPlace = { myNavController.navigate("add_place")}
                    )
                }
                composable(route = "add_place"){
                    AddPlaceScreen()
                }

            }
        }
    }
}

