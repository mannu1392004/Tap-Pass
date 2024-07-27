package com.example.tap_pass.screens.meetings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tap_pass.R
import com.example.tap_pass.screens.mainscreen.GifImage

@Composable
fun MeetingScreen() {
    Surface(modifier = Modifier.fillMaxSize(),
        color = Color.White) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {


            GifImage(drawableId = R.drawable.resource_import,
                modifier = Modifier.size(100.dp))


            Text(text = "You Have Entered The Office")
            Spacer(modifier = Modifier.height(30.dp))

            Surface(
                color = MaterialTheme.colorScheme.error,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
            ) {

                Text(
                    text = "Meetings",
                    modifier = Modifier.padding(16.dp)
                )
            }

            val list = listOf(
                "8:00 Am- Project Discussion",
                "9:00 Am- Project Presentation",
                "12:00 Am- Project Status"
            )

            list.forEach {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceDim,
                    shape = RoundedCornerShape(10.dp), modifier = Modifier.padding(10.dp)
                ) {
                    Text(
                        text = it,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
            Surface(
                color = MaterialTheme.colorScheme.error,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Office Update",
                    modifier = Modifier.padding(16.dp)
                )
            }

            val officeupdate = listOf("2nd Floor Water Cooler Not Working", "Entry System Upgraded")
            officeupdate.forEach {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceDim,
                    shape = RoundedCornerShape(10.dp), modifier = Modifier.padding(10.dp)
                ) {
                    Text(
                        text = it,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }


}
