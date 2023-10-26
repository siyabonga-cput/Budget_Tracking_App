package com.example.expensetrackingapp.ui.theme

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.expensetrackingapp.ExpenseApplication
import com.example.expensetrackingapp.ui.theme.expense.ExpenseDetailsViewModel
import com.example.expensetrackingapp.ui.theme.expense.ExpenseEditViewModel
import com.example.expensetrackingapp.ui.theme.expense.ExpenseEntryViewModel
import com.example.expensetrackingapp.ui.theme.home.HomeViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ExpenseEditViewModel
        initializer {
            ExpenseEditViewModel(
                this.createSavedStateHandle(),
                budgetApplication().container.expensesRepository
            )
        }
        // Initializer for ItemEntryViewModel
        initializer {
            ExpenseEntryViewModel(budgetApplication().container.expensesRepository)
        }

        // Initializer for ItemDetailsViewModel
        initializer {
            ExpenseDetailsViewModel(
                this.createSavedStateHandle(),
                budgetApplication().container.expensesRepository
            )
        }

        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(budgetApplication().container.expensesRepository)
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [ExpenseApplication].
 */
fun CreationExtras.budgetApplication(): ExpenseApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as ExpenseApplication)
