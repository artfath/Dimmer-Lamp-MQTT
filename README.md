# Dimmer Lamp MQTT

## Overview
Dimmer Lamp MQTT is mobile app (android) used to controlling lamp over internet or IoT.
This App controlling can on-off and brightness lamp device.
MQTT protocol use to conecting dimmer lamp as subscriber and android device as publisher.

Android app use jetpack compose to draw UI.

## Required IoT Components
- Subscriber: ESP32 + Dimmer Lamp Driver
- Publisher: Android Device
- MQTT 5.0 client tool - MQTTX for test
- free public MQTT broker : EMQX Cloud
  - Broker: broker.emqx.io
  - TCP Port: 1883
    
## Dependencies
```
  dependencyResolutionManagement {
    repositories {
      ...
      maven { url = uri("https://jitpack.io") }
    }
  }
```

```
dependencies {

    implementation ("org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.5")
    implementation ("com.github.hannesa2:paho.mqtt.android:3.3.5")
    
    implementation ("androidx.legacy:legacy-support-v4:1.0.0")
}
```

## Documentation

#### Dimmer Lamp App
<br />
<img src="https://github.com/artfath/Dimmer-Lamp-MQTT/assets/86766443/98840380-a795-4abf-b93f-98636b9b5066" width="400"><br />

#### MQTTX Dekstop App
<br />
<img src="https://github.com/artfath/Dimmer-Lamp-MQTT/assets/86766443/dcee6d10-9f57-484f-b6cc-eaa6a3ea7842" width="800">

## Asset

Lamp Image: Pierre Châtel-Innocenti on Unsplash https://unsplash.com/s/photos/lamp?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText
