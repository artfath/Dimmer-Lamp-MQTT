package com.example.dimmerlamp.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dimmerlamp.repository.MqttRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttException

class MainViewModel(
    private val mqttRepository: MqttRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
//        getTopic()
    }

    private fun getTopic() {
        _uiState.update { currentState ->
            currentState.copy(topic = "")
        }

    }

    fun connection() {
        try {
            mqttRepository.connect(object :IMqttActionListener{
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    _uiState.update { currentState ->
                        currentState.copy(connection = true)
                    }
                    Log.d(STATUS,"connect ${asyncActionToken?.client} ${_uiState.value.connection}")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    _uiState.update { currentState ->
                        currentState.copy(connection = false)
                    }
                    Log.d(STATUS,"connect failure")
                }
            })


        }catch (e: MqttException) {
            e.printStackTrace()
        }
    }
    fun disconection(){
        try {
            mqttRepository.disconnect(object :IMqttActionListener{
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    _uiState.update { currentState ->
                        currentState.copy(connection = false)
                    }
                    Log.d(STATUS,"disconnect ${_uiState.value.connection}")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(STATUS,"disconnect failure")
                }
            })
        }catch (e: MqttException) {
            e.printStackTrace()
        }
    }


    fun setBrightness(value: Int, topic: String) {
        viewModelScope.launch {
            try {
                _uiState.update { currentState ->
                    currentState.copy(brightnessValue = value.toFloat())
                }
                publishData(topic)
            } catch (e: MqttException) {
                e.printStackTrace()
            }
        }
    }

    fun setSwitch(value: String, topic: String) {
        viewModelScope.launch {
            try {
                _uiState.update { currentState ->
                    currentState.copy(switchValue = if (value == "true") true else false)
                }
                publishData(topic)
            } catch (e: MqttException) {
                e.printStackTrace()
            }
        }
    }
    private fun publishData(topic:String){
        val value = _uiState.value.switchValue.toString() + "|" + _uiState.value.brightnessValue.toString()
        mqttRepository.publish(topic,value,1,false)
    }
    fun updateTopic(value:String){
        _uiState.update { currentState ->
            currentState.copy(topic = value)}
    }

    companion object {
        const val STATUS = "status"
    }
}