package com.example.dimmerlamp.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dimmerlamp.DataApplication
import com.example.dimmerlamp.ui.home.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                mqttRepository = dataApplication().container.mqttRepository
            )
        }
    }
}

fun CreationExtras.dataApplication(): DataApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DataApplication)