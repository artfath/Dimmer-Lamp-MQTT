package com.example.dimmerlamp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dimmerlamp.repository.MqttRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.MqttException

class HomeViewModel(
    private val mqttRepository: MqttRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        connection()
    }

    private fun connection() {
        try {
            mqttRepository.connect()
        }catch (e: MqttException) {
            e.printStackTrace()
        }
    }


    fun setBrightness(value: Int) {
        viewModelScope.launch {
            try {

                _uiState.update { currentState ->
                    currentState.copy(brightnessValue = value.toFloat())
                }
                mqttRepository.publish(topic,value.toString(),1,false)
            } catch (e: MqttException) {
                e.printStackTrace()
            }
        }
    }

    fun setSwitch(value: String) {
        viewModelScope.launch {
            try {
                _uiState.update { currentState ->
                    currentState.copy(switchValue = if (value == "true") true else false)
                }
                mqttRepository.publish(topic,value,1,false)
            } catch (e: MqttException) {
                e.printStackTrace()
            }
        }
    }
    companion object {
        const val TAG = "AndroidMqttClient"
        const val topic = "data/switch"
    }
}