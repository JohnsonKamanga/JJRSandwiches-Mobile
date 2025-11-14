package com.example.jjrsandwiches_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.jjrsandwiches_mobile.data.network.RetrofitClient
import com.example.jjrsandwiches_mobile.data.repository.AuthRepository
import com.example.jjrsandwiches_mobile.data.sessions.SessionManager
import com.example.jjrsandwiches_mobile.navigation.getResult
import com.example.jjrsandwiches_mobile.ui.screens.Home
import com.example.jjrsandwiches_mobile.ui.screens.Results
import com.example.jjrsandwiches_mobile.ui.screens.Settings
import com.example.jjrsandwiches_mobile.ui.screens.SignIn
import com.example.jjrsandwiches_mobile.ui.screens.SignUp
import com.example.jjrsandwiches_mobile.ui.theme.JJRSandwichesMobileTheme
import com.example.jjrsandwiches_mobile.ui.viewmodel.AuthViewModel
import com.example.jjrsandwiches_mobile.ui.viewmodel.AuthViewModelFactory
import com.example.jjrsandwiches_mobile.ui.viewmodel.AuthState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var currentTheme by remember { mutableStateOf("System") }
            val isDarkTheme = when (currentTheme) {
                "Light" -> false
                "Dark" -> true
                else -> isSystemInDarkTheme()
            }

            val context = LocalContext.current
            val authRepository = remember { AuthRepository(RetrofitClient.instance, SessionManager(context)) }
            val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(authRepository))
            val authState by authViewModel.authState.collectAsState()

            JJRSandwichesMobileTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    LaunchedEffect(authState) {
                        if (authState is AuthState.SignedIn) {
                            navController.navigate("app") {
                                popUpTo("auth") { inclusive = true }
                            }
                        } else if (authState is AuthState.SignedOut) {
                            if (navController.currentBackStackEntry?.destination?.route != "sign-in") {
                                navController.navigate("auth") {
                                    popUpTo("app") { inclusive = true }
                                }
                            }
                        }
                    }

                    androidx.navigation.compose.NavHost(navController = navController, startDestination = "auth") {
                        navigation(startDestination = "sign-in", route = "auth") {
                            composable("sign-in") { SignIn(navController, authViewModel) }
                            composable("sign-up") { SignUp(navController, authViewModel) }
                        }
                        navigation(startDestination = "home", route = "app") {
                            composable("home") { Home(navController) }
                            composable("results/{resultId}") { backStackEntry ->
                                val resultId = backStackEntry.arguments?.getString("resultId")
                                val result = getResult(resultId.toString())
                                Results(result.verdict.toString(), result.summary, result.certaintyScore, result.sources, navController)
                            }
                            composable("settings") {
                                Settings(
                                    onSignOut = { authViewModel.signOut() },
                                    onThemeChange = { currentTheme = it },
                                    currentTheme = currentTheme
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}