package com.example.expensetrackingapp.data

import kotlinx.coroutines.flow.Flow

class OfflineExpensesRepository(private val expenseDAO: ExpenseDAO) : ExpensesRepository {
    override fun getAllExpensesStream(): Flow<List<Expense>> = expenseDAO.getAllExpenses()

    override fun getExpenseStream(id: Int): Flow<Expense?> = expenseDAO.getExpense(id)

    override suspend fun insertExpense(expense: Expense) = expenseDAO.insert(expense)

    override suspend fun deleteExpense(expense: Expense) = expenseDAO.delete(expense)

    override suspend fun updateExpense(expense: Expense) = expenseDAO.update(expense)
}
