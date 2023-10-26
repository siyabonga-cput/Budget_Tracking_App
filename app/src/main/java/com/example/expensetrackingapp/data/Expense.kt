

package com.example.expensetrackingapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "expenses")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val amount: Double,
    val date: String,
    val category: String,
    val description: String

)
