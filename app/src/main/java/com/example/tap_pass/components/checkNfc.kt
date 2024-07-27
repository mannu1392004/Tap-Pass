package com.example.tap_pass.components

import android.content.Context
import android.content.pm.PackageManager
import android.nfc.NfcAdapter

fun isNfcAvailable(context: Context): Boolean {
    val packageManager = context.packageManager
    val nfcAdapter = NfcAdapter.getDefaultAdapter(context)
    return ( nfcAdapter != null && packageManager.hasSystemFeature(PackageManager.FEATURE_NFC))
}