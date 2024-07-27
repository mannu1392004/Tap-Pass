import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NfcViewModel(private val context: Context) : ViewModel() {

    // nfc state
   private val nfcState1 = MutableStateFlow("")
    val nfcState: StateFlow<String> = nfcState1

    fun reset(){
        nfcState1.value = ""
    }

    private val nfcAdapter: NfcAdapter? = NfcAdapter.getDefaultAdapter(context)
    fun readNfc() {
        nfcAdapter?.enableReaderMode(
            context as FragmentActivity,
            NfcReaderCallback(),
            NfcAdapter.FLAG_READER_NFC_A or NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
            null
        )
    }

    // stop scanning
    fun stopNfc() {
        nfcAdapter?.disableReaderMode(context as FragmentActivity)
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun NfcReaderCallback(): NfcAdapter.ReaderCallback {
        return object : NfcAdapter.ReaderCallback {
            override fun onTagDiscovered(tag: Tag) {
                // Handle the discovered tag here
                val tagId = tag.id.toHexString()

                nfcState1.value = tagId
                Log.d("NFC", " You Are Authenticated to Office Nfc Tag ID: $tagId, Name:Mannu ")
                // You can also read tag data or communicate with the tag here
            }
        }
    }


    fun checkNfcAvailability():Int {
        if (nfcAdapter == null) {
            Toast.makeText(context, "NFC not available on this device!", Toast.LENGTH_SHORT).show()
            return 0

        } else if (!nfcAdapter.isEnabled) {
            Toast.makeText(context, "NFC is disabled!", Toast.LENGTH_SHORT).show()
            return 1
        } else {
            Toast.makeText(context, "NFC is available!", Toast.LENGTH_SHORT).show()
           return 2
        }
    }

    fun startNfcForegroundDispatch(activity: FragmentActivity) {
        val intent = Intent(activity, activity.javaClass).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING)
        val pendingIntent = PendingIntent.getActivity(activity, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val nfcIntentFilter = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
        }
        nfcAdapter?.enableForegroundDispatch(activity, pendingIntent, arrayOf(nfcIntentFilter), null)
    }

    fun stopNfcForegroundDispatch(activity: FragmentActivity) {
        nfcAdapter?.disableForegroundDispatch(activity)
    }
    fun processNfcIntent(intent: Intent) {

        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            val tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
            val ndef = Ndef.get(tag)
            // Process the NFC tag here
            Toast.makeText(context, "NFC tag detected!", Toast.LENGTH_SHORT).show()
        }
    }
}

class NfcViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(NfcViewModel::class.java)) {
            return NfcViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
