package com.example.expensetrackingapp.ui.theme.expense

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expensetrackingapp.ExpenseTopAppBar
import com.example.expensetrackingapp.R
import com.example.expensetrackingapp.ui.theme.AppViewModelProvider
import com.example.expensetrackingapp.ui.theme.ExpenseTrackingAppTheme
import com.example.expensetrackingapp.ui.theme.navigation.NavigationDestination
import kotlinx.coroutines.launch

object ExpenseEditDestination : NavigationDestination {
    override val route = "expense_edit"
    override val titleRes = R.string.edit_expense_title
    const val expenseIdArg = "itemId"
    val routeWithArgs = "$route/{$expenseIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ExpenseEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            ExpenseTopAppBar(
                title = stringResource(ExpenseEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        ExpenseEntryBody(
            expenseUiState = viewModel.expenseUiState,
            onExpenseValueChange = viewModel::updateUiState,
            onSaveClick = {

                coroutineScope.launch {
                    viewModel.updateItem()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseEditScreenPreview() {
    ExpenseTrackingAppTheme {
        ExpenseEditScreen(navigateBack = { /*Do nothing*/ }, onNavigateUp = { /*Do nothing*/ })
    }
}
