package com.example.manage.laundry.ui

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.manage.laundry.ui.component.colorButtons
import com.example.manage.laundry.ui.component.color_buttons.ColorButton
import com.example.manage.laundry.ui.component.dropletButtons
import com.example.manage.laundry.ui.component.wiggleButtonItems
import com.example.manage.laundry.ui.theme.LightPurple
import com.example.manage.laundry.ui.theme.Purple
import com.example.manage.laundry.ui.theme.RoyalPurple
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.balltrajectory.Straight
import com.exyte.animatednavbar.animation.balltrajectory.Teleport
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.StraightIndent
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.items.dropletbutton.DropletButton
import com.exyte.animatednavbar.items.wigglebutton.WiggleButton

@Composable
fun App() {
    Scaffold(
        bottomBar = { ColorButtonNavBar() }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {

        }
    }
}


@Composable
private fun ColorButtonNavBar() {
    var selectedItem by remember { mutableIntStateOf(0) }
    var prevSelectedIndex by remember { mutableIntStateOf(0) }

    AnimatedNavigationBar(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 60.dp)
            .height(85.dp),
        selectedIndex = selectedItem,
        ballColor = Color.White,
        cornerRadius = shapeCornerRadius(25.dp),
        ballAnimation = Straight(
            spring(dampingRatio = 0.6f, stiffness = Spring.StiffnessVeryLow)
        ),
        indentAnimation = StraightIndent(
            indentWidth = 56.dp,
            indentHeight = 15.dp,
            animationSpec = tween(1000)
        )
    ) {
        colorButtons.forEachIndexed { index, it ->
            ColorButton(
                modifier = Modifier.fillMaxSize(),
                prevSelectedIndex = prevSelectedIndex,
                selectedIndex = selectedItem,
                index = index,
                onClick = {
                    prevSelectedIndex = selectedItem
                    selectedItem = index
                },
                icon = it.icon,
                contentDescription = stringResource(id = it.description),
                animationType = it.animationType,
                background = it.animationType.background
            )
        }
    }
}

@Composable
private fun DropletButtonNavBar() {
    var selectedItem by remember { mutableIntStateOf(0) }
    AnimatedNavigationBar(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 40.dp)
            .height(85.dp),
        selectedIndex = selectedItem,
        ballColor = Color.White,
        cornerRadius = shapeCornerRadius(25.dp),
        ballAnimation = Parabolic(tween(Duration, easing = LinearOutSlowInEasing)),
        indentAnimation = Height(
            indentWidth = 56.dp,
            indentHeight = 15.dp,
            animationSpec = tween(
                DoubleDuration,
                easing = { OvershootInterpolator().getInterpolation(it) })
        )
    ) {
        dropletButtons.forEachIndexed { index, it ->
            DropletButton(
                modifier = Modifier.fillMaxSize(),
                isSelected = selectedItem == index,
                onClick = { selectedItem = index },
                icon = it.icon,
                dropletColor = Purple,
                animationSpec = tween(durationMillis = Duration, easing = LinearEasing)
            )
        }
    }
}

@Composable
private fun WiggleButtonNavBar(
    onItemClick: (Int) -> Unit
) {
    var selectedItem by remember { mutableIntStateOf(0) }

    AnimatedNavigationBar(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 40.dp)
            .height(85.dp),
        selectedIndex = selectedItem,
        ballColor = Color.White,
        cornerRadius = shapeCornerRadius(25.dp),
        ballAnimation = Teleport(tween(Duration, easing = LinearEasing)),
        indentAnimation = Height(
            indentWidth = 56.dp,
            indentHeight = 15.dp,
            animationSpec = tween(
                DoubleDuration,
                easing = { OvershootInterpolator().getInterpolation(it) })
        )
    ) {
        wiggleButtonItems.forEachIndexed { index, it ->
            WiggleButton(
                modifier = Modifier.fillMaxSize(),
                isSelected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    onItemClick(index)
                },
                icon = it.icon,
                backgroundIcon = it.backgroundIcon,
                wiggleColor = LightPurple,
                outlineColor = RoyalPurple,
                contentDescription = stringResource(id = it.description),
                enterExitAnimationSpec = tween(durationMillis = Duration, easing = LinearEasing),
                wiggleAnimationSpec = spring(dampingRatio = .45f, stiffness = 35f)
            )
        }
    }
}

const val Duration = 500
const val DoubleDuration = 1000