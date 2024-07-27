import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import com.example.tap_pass.R
import com.example.tap_pass.authenticator.BiometricHelper
import com.example.tap_pass.screens.Screen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun BiometricAuthScreen(navController: NavHostController) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var authenticationResult by remember { mutableStateOf<String?>(null) }

    val x = LocalContext.current

    val authenticated = remember {
        mutableIntStateOf(2)
    }

    LaunchedEffect(key1 = Unit) {
        if (BiometricHelper.isBiometricAvailable(context)) {
            BiometricHelper.showBiometricPrompt(
                activity = x as FragmentActivity,
                onSuccess = { authenticated.intValue = 0 },
                onError = { error ->
                    authenticated.intValue = 1
                    authenticationResult = error
                }
            )
        } else {
            authenticationResult = "Biometric authentication is not available"
            authenticated.intValue = 1
        }

    }




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(156.dp))

        if (authenticated.intValue == 2) {
            Image(painter = painterResource(id = R.drawable.fingerprint), contentDescription = "")

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Place Your Finger On The Scanner",
                style = MaterialTheme.typography.titleMedium
            )
        }

        if (
            authenticated.intValue == 1
        ) {
            Image(painter = painterResource(id = R.drawable.finger), contentDescription = "")

            Text(
                text = "Error: $authenticationResult",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {

                authenticated.intValue = 2

                if (BiometricHelper.isBiometricAvailable(context)) {
                    BiometricHelper.showBiometricPrompt(
                        activity = x as FragmentActivity,
                        onSuccess = { authenticated.intValue = 0 },
                        onError = { error ->
                            authenticated.intValue = 1
                            authenticationResult = error
                        }
                    )
                } else {
                    authenticationResult = "Biometric authentication is not available"
                    authenticated.intValue = 1
                }


            }) {
                Text(text = "Try Again")

            }
        }

        if (authenticated.intValue == 0) {
            val firebaseAuth = FirebaseAuth.getInstance()
            if (firebaseAuth.currentUser != null) {
                navController.navigate(Screen.MainScreen.name)
            } else {
                navController.navigate(Screen.Login.name)
            }
        }


    }
}