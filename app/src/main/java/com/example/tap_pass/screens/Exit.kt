package com.example.tap_pass.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavController
import com.example.tap_pass.screens.mainscreen.GifImage
import kotlinx.coroutines.delay
import com.example.tap_pass.R as R

@Composable
fun Exit(navController: NavController){
Surface(modifier = Modifier.fillMaxSize(),
    color = Color.White) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        GifImage(drawableId = R.drawable.exit)
        Text(text = "You Have Exited the Office Have A Nice Day Thanks",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(20.dp))

        Text(text = "Tap-Pass",
            style = MaterialTheme.typography.titleLarge,

            )

        LaunchedEffect(key1 = Unit) {
            delay(2000)
            navController.navigate(Screen.MainScreen.name)
        }

    }

}
}