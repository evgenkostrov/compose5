package com.example.compose5

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha.medium
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose5.ui.theme.Shapes

@ExperimentalMaterialApi
@Composable
fun ExpandableCard(){
    var expandedState by remember { mutableStateOf(false)}
    Card(
        modifier = Modifier.fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    delayMillis = 500,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = Shapes.medium,
        onClick = {
            expandedState=!expandedState
        }
    ){

    }
}

@Composable
@Preview
fun ExpandableCardPreview(){

}