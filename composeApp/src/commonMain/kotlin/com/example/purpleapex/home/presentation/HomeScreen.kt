package com.example.purpleapex.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource
import purpleapex.composeapp.generated.resources.Res
import purpleapex.composeapp.generated.resources.purple_apex_logo

@Composable
fun HomeScreenRoot() {
    HomeScreen()
}

@Composable
fun HomeScreen() {
    Image(
        painter = painterResource(Res.drawable.purple_apex_logo),
        contentDescription = "Purple Apex Logo",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}
