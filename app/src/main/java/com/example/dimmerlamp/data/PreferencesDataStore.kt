package com.example.dimmerlamp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesDataStore(private val context: Context) {

    val getValue: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[KEYDATA] ?: ""
        }

    suspend fun saveValue(data:String) {
        context.dataStore.edit { topic ->
            topic[KEYDATA] = data
        }
    }
    companion object{
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "TopicData")
        val KEYDATA = stringPreferencesKey("topic_data")
    }
}