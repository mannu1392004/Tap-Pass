package com.example.tap_pass

import NfcViewModel
import NfcViewModelFactory
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.compose.AppTheme
import com.example.tap_pass.components.isNfcAvailable
import com.example.tap_pass.screens.MainNavigation


class MainActivity : FragmentActivity() {
    private lateinit var nfcViewModel: NfcViewModel
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nfcViewModel = ViewModelProvider(this, NfcViewModelFactory(this))[NfcViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            AppTheme {
                Surface(color = Color.White) {
                    val nfcpresent = isNfcAvailable(this)

                    MainNavigation(nfcpresent, nfcViewModel, this)


                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        nfcViewModel.startNfcForegroundDispatch(this)
    }

    override fun onPause() {
        super.onPause()
        nfcViewModel.stopNfcForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        nfcViewModel.processNfcIntent(intent)
    }


}
