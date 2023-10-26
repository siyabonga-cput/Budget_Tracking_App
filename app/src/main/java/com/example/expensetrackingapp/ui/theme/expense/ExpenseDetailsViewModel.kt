package com.example.expensetrackingapp.ui.theme.expense

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetrackingapp.data.ExpensesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel to retrieve, update and delete an item from the [ExpensesRepository]'s data source.
 */
class ExpenseDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val expensesRepository: ExpensesRepository,
) : ViewModel() {

    private val expenseId: Int =
        checkNotNull(savedStateHandle[ExpenseDetailsDestination.expenseIdArg])

    /**
     * Holds the item details ui state. The data is retrieved from [ExpensesRepository] and mapped to
     * the UI state.
     */
    val uiState: StateFlow<ExpenseDetailsUiState> =
        expensesRepository.getExpenseStream(expenseId)
            .filterNotNull()
            .map {
                ExpenseDetailsUiState()
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ExpenseDetailsUiState()
            )

    /**
     * Reduces the item quantity by one and update the [ExpensesRepository]'s data source.
     */


    /**
     * Deletes the item from the [ExpensesRepository]'s data source.
     */
    suspend fun deleteExpense() {
        expensesRepository.deleteExpense(uiState.value.expenseDetails.toExpense())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * UI state for ItemDetailsScreen
 */
data class ExpenseDetailsUiState(
    val expenseDetails: ExpenseDetails = ExpenseDetails()
)
