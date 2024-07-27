package com.example.tap_pass.screens.mainscreen

import NfcViewModel
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.tap_pass.MainActivity
import com.example.tap_pass.R
import com.example.tap_pass.screens.Screen

@Composable
fun IfNfcPresent(
    nfcenableorNot: MutableIntState,
    nfcViewModel: NfcViewModel,
    mainActivity: MainActivity,
    navController: NavHostController,
    entered: MutableState<Boolean>
) {

    val state = nfcViewModel.nfcState.collectAsState()

if (state.value.isNotEmpty()){

    Log.d("NFC", state.value)
    if (state.value=="5c098916") {
        entered.value = !entered.value
if (entered.value) {
    navController.navigate(Screen.Meetings.name)
}
else{
    navController.navigate(Screen.Exit.name)
}
    nfcViewModel.stopNfc()
        nfcViewModel.reset()

    }
    else{
        navController.navigate(Screen.CoffeeMachine.name)
        nfcViewModel.stopNfc()
        nfcViewModel.reset()
    }

}




    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    if (nfcenableorNot.intValue == 1) {
        showDialog.value = true
    }
    if (nfcenableorNot.intValue == 2) {
        LaunchedEffect(Unit) {
            nfcViewModel.readNfc()
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {


            Spacer(modifier = Modifier.height(70.dp))

            Text(text = "Tap Your Device on The Receiver")

            Spacer(modifier = Modifier.height(30.dp))
            GifImage(
                drawableId =
                R.drawable.nfc,
                modifier = Modifier.size(150.dp)
            )


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                elevation = CardDefaults.elevatedCardElevation(10.dp)

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Tap Pass",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "",
                        modifier = Modifier
                            .size(120.dp)
                            .border(width = 1.dp, color = Color.White, shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Name- Mannu")
                    Text(text = "Email- mannu@tappass.com")
                    Text(text = "Phone- 9876543210")
                    Text(text = "Employee ID - 1892")


                }

            }


        }


    }

    if (showDialog.value) {
        showOpenSetting(showDialog, nfcViewModel, mainActivity, nfcenableorNot, context)
    }
}

@Composable
fun showOpenSetting(
    showDialog: MutableState<Boolean>,
    nfcViewModel: NfcViewModel,
    mainActivity: MainActivity,
    nfcenableorNot: MutableIntState,
    context: Context,

    ) {
    Dialog(onDismissRequest = {
        nfcenableorNot.intValue = nfcViewModel.checkNfcAvailability()
        nfcViewModel.startNfcForegroundDispatch(mainActivity)
    }) {

        Surface(
            modifier = Modifier.fillMaxWidth(), color = Color.White,
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(20.dp)
            ) {
                Text(text = "Please enable NFC")

                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = {
                        val intent = Intent(Settings.ACTION_NFC_SETTINGS)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)

                    }) {
                        Text(text = "Open Setting")
                    }

                    Button(onClick = {
                        showDialog.value = false
                        nfcenableorNot.intValue = nfcViewModel.checkNfcAvailability()
                        nfcViewModel.startNfcForegroundDispatch(mainActivity)
                    }) {
                        Text(text = "Enabled")


                    }

                }


            }

        }
    }

}
