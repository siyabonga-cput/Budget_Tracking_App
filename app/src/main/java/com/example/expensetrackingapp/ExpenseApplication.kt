package com.example.expensetrackingapp

import android.app.Application
import com.example.expensetrackingapp.data.AppContainer
import com.example.expensetrackingapp.data.AppDataContainer

class ExpenseApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
