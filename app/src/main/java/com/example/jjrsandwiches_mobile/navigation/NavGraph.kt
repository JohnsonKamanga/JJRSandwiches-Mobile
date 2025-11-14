package com.example.jjrsandwiches_mobile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jjrsandwiches_mobile.ui.screens.Home
import com.example.jjrsandwiches_mobile.ui.screens.Results
import com.example.jjrsandwiches_mobile.ui.screens.Settings

data class Result(
    val verdict: Boolean,
    val summary: String,
    val certaintyScore: Float,
    val sources: List<String>
)

fun getResult(id: String): Result{
    return Result(
         false,
        "The claim that 'drinking salt water cures COVID-19' is false. Multiple verified health sources confirm that there is no scientific evidence supporting this claim.",
        0.93f,
        listOf(
            "https://www.who.int/emergencies/diseases/novel-coronavirus-2019/advice-for-public",
            "https://www.healthline.com/health-news",
            "https://www.cdc.gov/coronavirus"
        )
    )
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    onThemeChange: (String) -> Unit, 
    currentTheme: String
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            Home(navController)
        }
        composable("results/{resultId}") {backStackEntry ->
            val resultId = backStackEntry.arguments?.getString("resultId")
            val result = getResult(resultId.toString())
            Results(result.verdict.toString(), result.summary, result.certaintyScore, result.sources, navController)
        }
        composable("settings") {
            Settings(
                onSignOut = {
                    navController.navigate("sign-in") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                onThemeChange = onThemeChange,
                currentTheme = currentTheme
            )
        }
    }
}