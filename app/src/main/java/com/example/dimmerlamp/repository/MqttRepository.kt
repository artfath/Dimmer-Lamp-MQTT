package com.example.dimmerlamp.repository

import android.util.Log
import info.mqtt.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage

interface MqttRepository {
    fun connect(listener:IMqttActionListener?)

    fun publish(topic: String, payload: String, qos: Int, isRetained: Boolean)
    fun subscribe(topic: String, qos: Int)
    fun disconnect(listener: IMqttActionListener?)
}
class MqttRepositoryImpl(private val mqttClient: MqttAndroidClient):MqttRepository{
    override fun connect(listener:IMqttActionListener?){
        mqttClient.setCallback(object : MqttCallback {
            override fun messageArrived(topic: String?, message: MqttMessage?) {
                Log.d("client", "Receive message: ${message.toString()} from topic: $topic")
            }

            override fun connectionLost(cause: Throwable?) {
                Log.d("client", "Connection lost ${cause.toString()}")
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {

            }
        })
        val options = MqttConnectOptions()
        try {
            mqttClient.connect(options, null, listener)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    override fun publish(topic: String, payload: String, qos: Int, isRetained: Boolean) {
        try {
            val message = MqttMessage()
            message.payload = payload.toByteArray()
            message.qos = qos
            message.isRetained = isRetained
            mqttClient.publish(topic, message, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d("client", "$payload successed publish to $topic")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d("client", "Failure publish $payload to $topic")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    override fun subscribe(topic: String, qos: Int) {
        TODO("Not yet implemented")
    }

    override fun disconnect(listener: IMqttActionListener?) {
        try{
            mqttClient.disconnect(null,listener)
        }catch (e:MqttException){
            e.printStackTrace()
        }
    }
}