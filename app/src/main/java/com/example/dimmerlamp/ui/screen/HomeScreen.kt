package com.example.dimmerlamp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dimmerlamp.R
import com.example.dimmerlamp.ui.component.CircleGraph
import com.example.dimmerlamp.ui.theme.Blue700
import com.example.dimmerlamp.ui.theme.Blue900
import com.example.dimmerlamp.ui.theme.DimmerLampTheme
import com.example.dimmerlamp.ui.theme.Neutral300
import com.example.dimmerlamp.ui.theme.Neutral600


@Composable
fun HomeScreen(
    navigateToConnection: () -> Unit,
    modifier: Modifier = Modifier,
    checked: Boolean,
    sliderPosition: Float,
    connected: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onSliderChange: (Float) -> Unit
) {
    HomeBody(
        modifier = modifier,
        sliderPosition = sliderPosition,
        checked = checked,
        connected = connected,
        onCheckedChange = onCheckedChange,
        onSliderChange = onSliderChange,
        navigateToConnection = navigateToConnection,
    )
}

@Composable
fun HomeBody(
    modifier: Modifier,
    sliderPosition: Float,
    checked: Boolean,
    connected: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onSliderChange: (Float) -> Unit,
    navigateToConnection: () -> Unit,
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .background(color = Color.White)
    ) {
//    Column(
//        modifier = modifier
//            .fillMaxWidth()
//
//    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(340.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF29B1F7), Color(0xFFE0F4FE)),
                        startY = 100f,
                    )
                ),
            horizontalArrangement = Arrangement.Center
        ) {

            Column {
                IconButton(
                    modifier = modifier
                        .padding(8.dp), onClick = navigateToConnection
                ) {
                    Icon(
                        painter = painterResource(id = if (connected) R.drawable.ic_signal else R.drawable.ic_signal_off),
                        tint = Color.White,
                        contentDescription = null
                    )
                }
                Text(
                    modifier = modifier.padding(top = 24.dp, start = 16.dp),
                    text = "Smart Lamp",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )
            }
            Spacer(modifier = modifier.width(16.dp))
            Image(
                modifier = modifier
                    .height(260.dp), painter = painterResource(id = R.drawable.light),
                contentDescription = null
            )
        }

//    }
        Box(
            modifier = modifier
                .padding(top = 280.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = modifier
                    .padding(bottom = 24.dp)
            ) {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircleGraph(
                        modifier = modifier,
                        values = sliderPosition
                    )
                }
                Slider(
                    enabled = if (connected.equals(true) && checked.equals(true)) true else false,
                    modifier = modifier
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    value = sliderPosition,
                    onValueChange = onSliderChange,
                    valueRange = 0f..100f,
                    colors = SliderDefaults.colors(
                        thumbColor = Blue700,
                        activeTrackColor = Blue900
                    )
                )
                SwitchCard(
                    modifier = modifier,
                    checked = checked,
                    enabled = connected,
                    onCheckedChange = onCheckedChange
                )
                WattMeterCard(modifier = modifier, value = sliderPosition)
            }
        }


    }
}


@Composable
fun SwitchCard(
    modifier: Modifier,
    checked: Boolean,
    enabled: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .shadow(elevation = 6.dp, ambientColor = Color.Gray,
                shape = RoundedCornerShape(15.dp))
            .background(color = Color.White)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
                Text(
                    modifier = modifier,
                    text = "Light in The Room",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Text(
                    modifier = modifier.padding(top = 8.dp),
                    text = if (checked) "Turn On" else "Turn Off",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
            }
            Box(
                modifier = Modifier.weight(0.4f),
                contentAlignment = Alignment.Center
            ) {
                Switch(
                    checked = checked,
                    enabled = enabled,
                    onCheckedChange = onCheckedChange,
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = Blue700,
                        uncheckedBorderColor = Neutral300,
                        uncheckedTrackColor = Neutral300,
                        uncheckedThumbColor = Neutral600,
                        disabledUncheckedBorderColor = Neutral600
                    )
                )
            }
        }

    }
}

@Composable
fun WattMeterCard(modifier: Modifier, value: Float) {
    val wattage = ((value / 100f) * 3)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .shadow(elevation = 6.dp, ambientColor = Color.Gray,
                shape = RoundedCornerShape(15.dp))

//            .clip(RoundedCornerShape(15.dp))
            .background(color = Color.White)

    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = modifier
                    .weight(1f),
                text = "Energy",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Row(
                modifier = modifier
                    .weight(0.9f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    text = "%.1f".format(wattage),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Blue900
                )
                Text(
                    modifier = modifier.weight(1f),
                    text = "Watt",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    DimmerLampTheme(darkTheme = false) {
        HomeBody(
            modifier = Modifier,
            sliderPosition = 80f,
            checked = true,
            connected = false,
            onCheckedChange = {},
            onSliderChange = {},
            navigateToConnection = {},
        )
    }
}