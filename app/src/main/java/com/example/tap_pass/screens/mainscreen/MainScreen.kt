package com.example.tap_pass.screens.mainscreen

import NfcViewModel
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.tap_pass.MainActivity


@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun MainScreen(
    navController: NavHostController,
    nfcpresent: Boolean,
    nfcViewModel: NfcViewModel,
    mainActivity: MainActivity,
    entered: MutableState<Boolean>
) {
    val context = LocalContext.current
    val nfcenableorNot = remember {
        mutableIntStateOf(-1)
    }


    LaunchedEffect(Unit) {
        nfcenableorNot.intValue = nfcViewModel.checkNfcAvailability()
        nfcViewModel.startNfcForegroundDispatch(mainActivity)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
    ) {


        if (nfcenableorNot.intValue == -1) {
            LoadinScreen()
        } else if (nfcenableorNot.intValue == 0) {
            BluetoothPresnt()
        } else {
            IfNfcPresent(nfcenableorNot, nfcViewModel, mainActivity,navController,entered)

            Icon(imageVector = Icons.Default.Refresh,
                contentDescription = "",
                modifier = Modifier
                    .padding(30.dp)
                    .align(Alignment.TopEnd)
                    .clickable {
                        nfcenableorNot.intValue = -1
                        nfcenableorNot.intValue = nfcViewModel.checkNfcAvailability()
                        nfcViewModel.startNfcForegroundDispatch(mainActivity)
                    })

        }


    }
}

@Composable
fun LoadinScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        CircularProgressIndicator(modifier = Modifier.size(50.dp))
    }
}


@Composable
fun GifImage(
    modifier: Modifier = Modifier,
    drawableId: Int
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context).data(data = drawableId).apply(block = {

            }).build(), imageLoader = imageLoader
        ),
        contentDescription = null,
        modifier = modifier.fillMaxWidth(),
    )
}