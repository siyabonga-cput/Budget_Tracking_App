package com.example.expensetrackingapp.ui.theme.expense

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.expensetrackingapp.data.Expense
import com.example.expensetrackingapp.data.ExpensesRepository
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * ViewModel to validate and insert items in the Room database.
 */
class ExpenseEntryViewModel(private val expensesRepository: ExpensesRepository) : ViewModel() {

    /**
     * Holds current item ui state
     */
    var expenseUiState by mutableStateOf(ExpenseUiState())
        private set

    /**
     * Updates the [expenseUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    fun updateUiState(expenseDetails: ExpenseDetails) {
        expenseUiState =
            ExpenseUiState(expenseDetails = expenseDetails, isEntryValid = validateInput(expenseDetails))
    }

    /**
     * Inserts an [Expense] in the Room database
     */
    suspend fun saveExpense() {
        if (validateInput()) {
            expensesRepository.insertExpense(expenseUiState.expenseDetails.toExpense())
        }
    }

    private fun validateInput(uiState: ExpenseDetails = expenseUiState.expenseDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && amount.isNotBlank() && category.isNotBlank() &&
                    date.isNotBlank() && isValidDate(date) && description.isNotBlank()
        }
    }

    private fun isValidDate(dateStr: String): Boolean {
        try {
            SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(dateStr)
            return true
        } catch (e: Exception) {
            return false
        }
    }
}

/**
 * Represents Ui State for an Expense.
 */
data class ExpenseUiState(
    val expenseDetails: ExpenseDetails = ExpenseDetails(),
    val isEntryValid: Boolean = false
)

data class ExpenseDetails(
    val id: Int = 0,
    val name: String = "",
    val amount: String = "",
    val category: String = "",
    val date: String = "", // Change to String
    val description: String = ""
)

/**
 * Extension function to convert [ExpenseDetails] to [Expense]. If the value of [ExpenseDetails.amount] is
 * not a valid [Double], then the amount will be set to 0.0.
 */
fun ExpenseDetails.toExpense(): Expense = Expense(
    id = id,
    name = name,
    amount = amount.toDoubleOrNull() ?: 0.0,
    category = category,
    date = date,
    description = description
)

fun Expense.formattedAmount(): String {
    return NumberFormat.getCurrencyInstance().format(amount)
}

/**
 * Extension function to convert [Expense] to [ExpenseUiState]
 */
fun Expense.toExpenseUiState(isEntryValid: Boolean = false): ExpenseUiState = ExpenseUiState(
    expenseDetails = this.toExpenseDetails(),
    isEntryValid = isEntryValid
)

/**
 * Extension function to convert [Expense] to [ExpenseDetails]
 */
fun Expense.toExpenseDetails(): ExpenseDetails = ExpenseDetails(
    id = id,
    name = name,
    amount = amount.toString(),
    category = category,
    date = date,
    description = description
)