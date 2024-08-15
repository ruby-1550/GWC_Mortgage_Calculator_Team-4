package com.example.mortgagecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.material3.Scaffold
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mortgagecalculator.screens.HomeDetailsScreen
import com.example.mortgagecalculator.screens.MortgageCalculatorScreen
import com.example.mortgagecalculator.screens.SignInScreen
import com.example.mortgagecalculator.screens.sampleHouses
import com.example.mortgagecalculator.ui.theme.MortgageCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MortgageCalculatorTheme {
                Scaffold { innerPadding ->
                    AppNavigation()  // Removed unused modifier
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {  // Removed unused modifier
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "signIn") {
        composable("signIn") {
            SignInScreen(onSignInSuccess = { navController.navigate("homeDetails") })
        }
        composable("homeDetails") {
            HomeDetailsScreen(houses = sampleHouses(), onSelectHouse = { house ->
                navController.navigate("mortgageCalculator/${house.price}")
            })
        }
        composable("mortgageCalculator/{housePrice}") { backStackEntry ->
            val housePrice = backStackEntry.arguments?.getString("housePrice")
            val house = sampleHouses().find { it.price == housePrice }
            house?.let {
                MortgageCalculatorScreen(house = it)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MortgageCalculatorTheme {
        AppNavigation()
    }
}
