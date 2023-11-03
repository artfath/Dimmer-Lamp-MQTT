package com.example.dimmerlamp.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.dimmerlamp.ui.theme.Blue700
import com.example.dimmerlamp.ui.theme.DimmerLampTheme
import com.example.dimmerlamp.ui.theme.Neutral300

@Composable
fun CircleGraph(modifier: Modifier,
                values:Float){
    Box(modifier = modifier) {
        CircleProgress(values = values)
        Column(modifier = modifier
            .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
//                modifier = modifier.align(Alignment.Center),
                text = values.toInt().toString()+ " " + "%",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF29B1F7)
            )
            Text(
//                modifier = modifier.align(Alignment.Center),
                text = "Brightness",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }

    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun CircleProgress(
    modifier: Modifier = Modifier,
    values: Float,
    fillColour: Color = Blue700,
    bacgroundColour: Color = Neutral300,
    width: Dp = 120.dp,
    height: Dp = 120.dp
) {
    val stroke = with(LocalDensity.current) { Stroke(6.dp.toPx(), cap = StrokeCap.Round) }
    val percent = (values/100) * 360f
    val valuePercent = values.toString()

    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult =  textMeasurer.measure(text = AnnotatedString(valuePercent))
    val textSize = textLayoutResult.size
    Canvas(modifier = modifier
        .requiredSize(width = width, height = height)) {
        val innerRadius = (size.minDimension - stroke.width) / 2
        val halfSize = size / 2.0f
        val topLeft = Offset(
            halfSize.width - innerRadius,
            halfSize.height - innerRadius
        )
        val startAngle = -90f
//values.forEachIndexed { index, fl ->
        drawCircle(color = Color.White, radius = innerRadius)
        drawArc(
            color = bacgroundColour,
            style = stroke,
            size = Size(innerRadius * 2, innerRadius * 2),
            startAngle = startAngle,
            sweepAngle = 360f,
            topLeft = topLeft,
            useCenter = false,

            )
        drawArc(
            color = fillColour,
            style = stroke,
            size = Size(innerRadius * 2, innerRadius * 2),
            startAngle = startAngle,
            sweepAngle = percent,
            topLeft = topLeft,
            useCenter = false

        )
//        drawText(textMeasurer = textMeasurer,
//            text = valuePercent,
//            style = TextStyle.Default,
//            topLeft = Offset(
//                (size.width - textSize.width) / 2,
//                (size.height - textSize.height) / 2
//            )
//        )
//}
    }
}

private enum class AnimatedCircleProgress { START, END }

@Preview
@Composable
fun AnimatedCirclePreview() {
    DimmerLampTheme(darkTheme = false) {
//        AnimatedGraph(
//            values = 75f
////            modifier = Modifier
////                .size(width = 120.dp, height = 120.dp)
//        )
        CircleGraph(modifier = Modifier, values = 80f)
    }
}