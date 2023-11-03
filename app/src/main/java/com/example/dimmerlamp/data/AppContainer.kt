package com.example.dimmerlamp.data

import android.content.Context
import com.example.dimmerlamp.repository.MqttRepository
import com.example.dimmerlamp.repository.MqttRepositoryImpl
import info.mqtt.android.service.MqttAndroidClient

interface AppContainer {
    val mqttRepository:MqttRepository
}
class DefaultAppContainer(context: Context):AppContainer {

    private val serverURI = "tcp://broker.emqx.io:1883"
    private val mqttClient = MqttAndroidClient(context, serverURI, "kotlin_client")

    override val mqttRepository: MqttRepository by lazy {
        MqttRepositoryImpl(mqttClient)
    }
}