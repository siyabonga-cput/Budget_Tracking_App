package com.example.expensetrackingapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface ExpenseDAO {

    @Query("SELECT * from expenses ORDER BY name ASC")
    fun getAllExpenses(): Flow<List<Expense>>

    @Query("SELECT * from expenses WHERE id = :id")
    fun getExpense(id: Int): Flow<Expense>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(expense: Expense)

    @Update
    suspend fun update(expense: Expense)

    @Delete
    suspend fun delete(expense: Expense)
}
