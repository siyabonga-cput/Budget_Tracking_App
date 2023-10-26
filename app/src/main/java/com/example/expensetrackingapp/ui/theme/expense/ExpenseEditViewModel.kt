package com.example.expensetrackingapp.ui.theme.expense

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetrackingapp.data.ExpensesRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * ViewModel to retrieve and update an item from the [ExpensesRepository]'s data source.
 */
class ExpenseEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val expensesRepository: ExpensesRepository
) : ViewModel() {

    /**
     * Holds current item ui state
     */
    var expenseUiState by mutableStateOf(ExpenseUiState())
        private set

    private val expenseId: Int = checkNotNull(savedStateHandle[ExpenseEditDestination.expenseIdArg])

    init {
        viewModelScope.launch {
            expenseUiState = expensesRepository.getExpenseStream(expenseId)
                .filterNotNull()
                .first()
                .toExpenseUiState(true)
        }
    }

    /**
     * Update the item in the [ExpensesRepository]'s data source
     */
    suspend fun updateItem() {
        if (validateInput(expenseUiState.expenseDetails)) {
            expensesRepository.updateExpense(expenseUiState.expenseDetails.toExpense())
        }
    }

    /**
     * Updates the [expenseUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    fun updateUiState(expenseDetails: ExpenseDetails) {
        expenseUiState =
            ExpenseUiState(
                expenseDetails = expenseDetails,
                isEntryValid = validateInput(expenseDetails)
            )
    }

    private fun validateInput(uiState: ExpenseDetails = expenseUiState.expenseDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && amount.isNotBlank() && category.isNotBlank() && date.isNotBlank() && description.isNotBlank()
        }
    }
}
