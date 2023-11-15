package com.example.dimmerlamp.ui.screen

data class UiState(
    val switchValue:Boolean = false,
    val brightnessValue: Float = 0f,
    val connection:Boolean = false,
    val topic:String = "",
    val isVisibleTopic:Boolean = true
)
