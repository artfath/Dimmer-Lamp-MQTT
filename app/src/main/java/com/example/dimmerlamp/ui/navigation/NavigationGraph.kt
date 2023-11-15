package com.example.dimmerlamp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dimmerlamp.data.PreferencesDataStore
import com.example.dimmerlamp.ui.AppViewModelProvider
import com.example.dimmerlamp.ui.screen.HomeScreen
import com.example.dimmerlamp.ui.screen.MainViewModel
import com.example.dimmerlamp.ui.screen.TopicScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val dataUiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current
    val store = PreferencesDataStore(context)
    val prefValue by store.getValue.collectAsState(initial = "")
    NavHost(
        navController = navController,
        startDestination = NavItem.Home.route,
        modifier = modifier
    ) {

        composable(route = NavItem.Home.route) {
            HomeScreen(
                navigateToConnection = { navController.navigate(NavItem.Topic.route) },
                checked = dataUiState.switchValue,
                sliderPosition = dataUiState.brightnessValue,
                connected = dataUiState.connection,
                onCheckedChange = { check -> viewModel.setSwitch(check.toString(), prefValue) },
                onSliderChange = { viewModel.setBrightness(it.toInt(), prefValue) }
            )

        }
        composable(route = NavItem.Topic.route) {
            TopicScreen(
                status = dataUiState.connection,
                topic = dataUiState.topic,
                prefValue = prefValue,
                onTopicChange = { viewModel.updateTopic(it) },
                onDisconnect = {
                    viewModel.disconection()
                    navController.navigateUp()
                },
                onConnect = {
                    viewModel.connection()
//                    if (dataUiState.connection) {
                        navController.navigate(NavItem.Home.route)
//                    }
                },
                onNavigateUp = { navController.navigateUp() })
        }
    }

}