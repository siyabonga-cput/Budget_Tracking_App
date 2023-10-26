package com.example.expensetrackingapp.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val expensesRepository: ExpensesRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineExpensesRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [ExpensesRepository]
     */
    override val expensesRepository: ExpensesRepository by lazy {
        OfflineExpensesRepository(ExpenseDatabase.getDatabase(context).expenseDAO())
    }
}
