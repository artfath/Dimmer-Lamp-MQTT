package com.example.dimmerlamp.data

import android.content.Context
import com.example.dimmerlamp.repository.MqttRepository
import com.example.dimmerlamp.repository.MqttRepositoryImpl
import info.mqtt.android.service.MqttAndroidClient
import kotlin.random.Random

interface AppContainer {
    val mqttRepository:MqttRepository
}
class DefaultAppContainer(context: Context):AppContainer {
    //random client id
    private val serverURI = "tcp://broker.emqx.io:1883"
    private val mqttClient = MqttAndroidClient(context, serverURI, clientId())

    override val mqttRepository: MqttRepository by lazy {
        MqttRepositoryImpl(mqttClient)
    }
    private fun clientId():String{
        val number = Random.nextInt(0,10000)
        val char = ('A'..'Z').random()
        return "app_client_" + char + number.toString()
    }
}