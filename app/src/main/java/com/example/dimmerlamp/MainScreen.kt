package com.example.dimmerlamp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.dimmerlamp.ui.home.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(){
    Scaffold { innerPadding ->
        HomeScreen(modifier = Modifier.padding(innerPadding))

    }
}