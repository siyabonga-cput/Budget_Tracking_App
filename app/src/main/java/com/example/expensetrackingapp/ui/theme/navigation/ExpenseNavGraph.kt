package com.example.expensetrackingapp.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.expensetrackingapp.ui.theme.expense.ExpenseDetailsDestination
import com.example.expensetrackingapp.ui.theme.expense.ExpenseDetailsScreen
import com.example.expensetrackingapp.ui.theme.expense.ExpenseEditDestination
import com.example.expensetrackingapp.ui.theme.expense.ExpenseEditScreen
import com.example.expensetrackingapp.ui.theme.expense.ExpenseEntryDestination
import com.example.expensetrackingapp.ui.theme.expense.ExpenseEntryScreen
import com.example.expensetrackingapp.ui.theme.home.HomeDestination
import com.example.expensetrackingapp.ui.theme.home.HomeScreen


/**
 * Provides Navigation graph for the application.
 */
@Composable
fun ExpenseNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(ExpenseEntryDestination.route) },
                navigateToItemUpdate = {
                    navController.navigate("${ExpenseDetailsDestination.route}/${it}")
                }
            )
        }
        composable(route = ExpenseEntryDestination.route) {
            ExpenseEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = ExpenseDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ExpenseDetailsDestination.expenseIdArg) {
                type = NavType.IntType
            })
        ) {
            ExpenseDetailsScreen(
                navigateToEditItem = { navController.navigate("${ExpenseEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = ExpenseEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ExpenseEditDestination.expenseIdArg) {
                type = NavType.IntType
            })
        ) {
            ExpenseEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}
