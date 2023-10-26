package com.example.expensetrackingapp.ui.theme.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expensetrackingapp.ExpenseTopAppBar
import com.example.expensetrackingapp.R
import com.example.expensetrackingapp.data.Expense
import com.example.expensetrackingapp.ui.theme.AppViewModelProvider
import com.example.expensetrackingapp.ui.theme.ExpenseTrackingAppTheme
import com.example.expensetrackingapp.ui.theme.expense.formattedAmount
import com.example.expensetrackingapp.ui.theme.navigation.NavigationDestination


object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

/**
 * Entry route for Home screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToItemUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ExpenseTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.expense_entry_title)
                )
            }
        },
    ) { innerPadding ->
        HomeBody(
            expenseList = homeUiState.expenseList,
            onExpenseClick = navigateToItemUpdate,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
private fun HomeBody(
    expenseList: List<Expense>, onExpenseClick: (Int) -> Unit, modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (expenseList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_expense_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            ExpenseList(
                expenseList = expenseList,
                onExpenseClick = { onExpenseClick(it.id) },
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
private fun ExpenseList(
    expenseList: List<Expense>, onExpenseClick: (Expense) -> Unit, modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items = expenseList, key = { it.id }) { expense ->
            BudgetExpense(
                expense = expense,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onExpenseClick(expense) })

        }
    }
}


@Composable
private fun BudgetExpense(
    expense: Expense, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = expense.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = expense.formattedAmount(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = expense.category, // Add category field
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = expense.date, // Add date field
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = expense.description, // Add description field
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    ExpenseTrackingAppTheme {
        HomeBody(listOf(
            Expense(1, "Movie", 100.0, "2023-11-16", "Entertainment", "Cinema Movie"),
            Expense(2, "Food", 1000.0, "2023-11-16","Home","Food for the whole month"),
            Expense(3, "Transport", 500.0, "2023-11-16","Transport","school Transport")

        ), onExpenseClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyEmptyListPreview() {
    ExpenseTrackingAppTheme {
        HomeBody(listOf(), onExpenseClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun BudgetExpensePreview() {
    ExpenseTrackingAppTheme {
        BudgetExpense(
            Expense(1, "Movie", 100.0, "2023-11-16","Entertainment","Cinema Movie"),
        )
    }
}
