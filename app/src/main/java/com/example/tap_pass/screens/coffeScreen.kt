package com.example.tap_pass.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tap_pass.R
import com.example.tap_pass.screens.mainscreen.GifImage
import kotlinx.coroutines.delay

@Composable
fun CoffeeMachine(navController: NavHostController) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            GifImage(drawableId = R.drawable.coffee_machine)

            Text(text = "Hold On While We Are Preparing A Cup Of  Coffee For You ",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(20.dp))

            CircularProgressIndicator(modifier = Modifier.padding(20.dp))

            LaunchedEffect(key1 = Unit) {
                delay(2000)
                navController.navigate(Screen.MainScreen.name)
            }

        }

    }
}