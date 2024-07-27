package com.example.tap_pass.screens.loginscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.tap_pass.R
import com.example.tap_pass.screens.Screen
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun LoginScreen(navController: NavHostController) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val error = remember { mutableStateOf("") }

    if (error.value.isNotEmpty()) {
        DialogScreen(error)
    }

    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Image(
                painter = painterResource(id = R.drawable.contactless), contentDescription = "",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .shadow(
                        elevation = 25.dp,
                        ambientColor = Color(0xFF9C27B0), spotColor = Color(0xFF673AB7)
                    )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Tap Pass", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(60.dp))

            OutlinedTextField(
                value = username.value,
                onValueChange = { username.value = it },
                label = { Text(text = "Username") },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(value = password.value, onValueChange = {
                password.value = it
            },
                label = {
                    Text(text = "Password")
                },
                singleLine = true)

            Spacer(modifier = Modifier.height(40.dp))

            Button(onClick = {

                Firebase.auth.signInWithEmailAndPassword(
                    username.value,
                    password.value
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        navController.navigate(Screen.MainScreen.name)
                    } else {
                        error.value = task.exception?.message.toString()
                    }
                }

            }) {
                Text(text = "Login")
            }

        }
    }


}

@Composable
fun DialogScreen(value: MutableState<String>) {

    Dialog(onDismissRequest = { value.value = "" }) {

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(30.dp)
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Error",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(20.dp),
                    color = Color.Red
                )

                Text(
                    text = value.value,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(20.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )

                Button(onClick = { value.value = "" }) {

                    Text(
                        text = "Ok",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(20.dp),
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

            }
        }

    }

}
