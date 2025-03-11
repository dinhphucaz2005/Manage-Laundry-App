package com.example.manage.laundry.ui.staff

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.manage.laundry.di.fakeViewModel
import com.example.manage.laundry.ui.theme.ManageLaundryAppTheme
import com.example.manage.laundry.viewmodel.StaffViewModel
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.Pie

@Composable
fun AwesomeAllScreen(viewModel: StaffViewModel = fakeViewModel<StaffViewModel>()) {
    var data by remember {
        mutableStateOf(
            listOf(
                Pie(label = "Task A", data = 30.0, color = Color.Red, selectedColor = Color.Green),
                Pie(label = "Task B", data = 50.0, color = Color.Cyan, selectedColor = Color.Blue),
                Pie(label = "Task C", data = 20.0, color = Color.Gray, selectedColor = Color.Yellow),
            )
        )
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PieChart(
            modifier = Modifier.size(200.dp),
            data = data,
            onPieClick = {
                println("${it.label} Clicked")
                val pieIndex = data.indexOf(it)
                data =
                    data.mapIndexed { mapIndex, pie -> pie.copy(selected = pieIndex == mapIndex) }
            },
            selectedScale = 1.56f,
            scaleAnimEnterSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            ),
            colorAnimEnterSpec = tween(300),
            colorAnimExitSpec = tween(300),
            scaleAnimExitSpec = tween(300),
            spaceDegreeAnimExitSpec = tween(300),
            selectedPaddingDegree = 4f,
            style = Pie.Style.Stroke()
        )

        LineChart(
            modifier = Modifier.fillMaxWidth().weight(1f).padding(horizontal = 22.dp),
            data = remember {
                listOf(
                    Line(
                        label = "Task Progress",
                        values = listOf(10.0, 20.0, 30.0, 40.0, 50.0),
                        color = SolidColor(Color(0xFF23af92)),
                        firstGradientFillColor = Color(0xFF2BC0A1).copy(alpha = .5f),
                        secondGradientFillColor = Color.Transparent,
                        strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                        gradientAnimationDelay = 1000,
                        drawStyle = DrawStyle.Stroke(width = 2.dp),
                    )
                )
            },
            animationMode = AnimationMode.Together(delayBuilder = {
                it * 500L
            }),
        )
    }
}

@Preview
@Composable
private fun AwesomeAllScreenPreview() {
    ManageLaundryAppTheme {
        AwesomeAllScreen()
    }
}