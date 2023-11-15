package com.example.dimmerlamp.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dimmerlamp.R
import com.example.dimmerlamp.data.PreferencesDataStore
import com.example.dimmerlamp.ui.theme.Blue700
import com.example.dimmerlamp.ui.theme.Blue900
import com.example.dimmerlamp.ui.theme.DimmerLampTheme
import kotlinx.coroutines.launch

@Composable
fun TopicScreen(
    modifier: Modifier = Modifier,
    status: Boolean,
    topic: String,
    prefValue: String,
    onTopicChange: (String) -> Unit,
    onDisconnect: () -> Unit,
    onConnect: () -> Unit,
    onNavigateUp:() ->Unit
) {
    val context = LocalContext.current
    val store = PreferencesDataStore(context)
//    val prefValue by store.getValue.collectAsState(initial = "")
    TopicBody(
        modifier = modifier,
        status = status,
        dataStore = store,
        prefValue = prefValue,
        topic = topic,
        onTopicChange = onTopicChange,
        onDisconnect = onDisconnect,
        onConnect = onConnect,
        onNavigateUp = onNavigateUp
    )
}

@Composable
fun TopicBody(
    modifier: Modifier,
    status: Boolean,
    dataStore: PreferencesDataStore,
    prefValue: String,
    topic: String,
    onTopicChange: (String) -> Unit,
    onDisconnect: () -> Unit,
    onConnect: () -> Unit,
    onNavigateUp:() ->Unit
) {

    Column(
        modifier = modifier
            .fillMaxWidth(1f)
            .fillMaxHeight()
            .background(color = Color.White)
            .padding(16.dp)
    ) {
        IconButton(onClick = onNavigateUp) {
            Icon(
                painter = painterResource(id =  R.drawable.ic_arrow_back ),
                tint = Color.Black,
                contentDescription = null
            )
        }
        Text(
            modifier = modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            text = stringResource(
                R.string.status_connection,
                if (status) "Connected" else "Disconnected"
            ),
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
        TopicItem(
            modifier = modifier,
            topic = topic,
            dataStore = dataStore,
            prefValue = prefValue,
            onTopicChange = onTopicChange
        )


        Row(
            modifier = modifier
                .weight(1f)
                .padding(top = 32.dp)
        ) {
            OutlinedButton(
                modifier = modifier
                    .weight(1f)
                    .height(56.dp),
                shape = RoundedCornerShape(10.dp),
                enabled = status,
                border = BorderStroke(2.dp, if (status) Blue700 else Color.Gray),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Blue700),
                onClick = onDisconnect
            ) {
                Text(text = "Disconnect")
            }
            Spacer(modifier = modifier.width(16.dp))
            Button(
                modifier = modifier
                    .weight(1f)
                    .height(56.dp),
                shape = RoundedCornerShape(10.dp),
                enabled = !status,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue700,
                    contentColor = Color.White),
                onClick = onConnect
            ) {
                Text(text = "Connect")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicItem(
    modifier: Modifier,
    topic: String,
    dataStore: PreferencesDataStore,
    prefValue: String,
    onTopicChange: (String) -> Unit,
) {
    val scope = rememberCoroutineScope()
    var showEditTopic by rememberSaveable {
        mutableStateOf(true)
    }
    if (showEditTopic) {
        Row {
            Text(
                modifier = modifier
                    .padding(top = 16.dp)
                    .weight(1f),
                text = stringResource(R.string.topic_value, prefValue),
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )
            IconButton(onClick = {
                showEditTopic = !showEditTopic
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    tint = Blue900,
                    contentDescription = null
                )
            }
        }
    } else {

        Row(
            modifier = modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                modifier = modifier
                    .weight(1f),
                label = { Text(text = stringResource(R.string.topic)) },
                value = topic,
                onValueChange = onTopicChange,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Blue700,
                    focusedLabelColor = Blue700)
            )
            IconButton(onClick = {
                scope.launch {
                    dataStore.saveValue(topic)
                }
                showEditTopic = !showEditTopic
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    tint = Blue900,
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopicScreenPreview() {
    DimmerLampTheme(darkTheme = false) {
        TopicBody(modifier = Modifier,
            status = true,
            topic = "data/switch",
            dataStore = PreferencesDataStore(LocalContext.current),
            prefValue = "",
            onTopicChange = {},
            onDisconnect = { },
            onConnect = { },
            onNavigateUp = {})
    }
}
@Preview(showBackground = true)
@Composable
fun TopicScreenEditPreview() {
    DimmerLampTheme(darkTheme = false) {
        TopicBody(modifier = Modifier,
            status = false,
            topic = "data/switch",
            dataStore = PreferencesDataStore(LocalContext.current),
            prefValue = "",
            onTopicChange = {},
            onDisconnect = { },
            onConnect = { },
            onNavigateUp = {})
    }
}
