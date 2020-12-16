package workshop.g_s.motiv8ai.ui.groceries

import android.os.Build
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.neovisionaries.ws.client.WebSocket
import com.neovisionaries.ws.client.WebSocketAdapter
import com.neovisionaries.ws.client.WebSocketFactory
import com.neovisionaries.ws.client.WebSocketFrame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import workshop.g_s.motiv8ai.data.AppConstants.WSS_URL
import workshop.g_s.motiv8ai.data.model.Grocery

class GroceriesViewModel : ViewModel() {

    private lateinit var ws: WebSocket

    val currentGrocery: MutableLiveData<Grocery> by lazy {
        MutableLiveData<Grocery>()
    }

    val isSocketAlreadyOpen: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun onStartButtonClick(editText: EditText) {
        if (this::ws.isInitialized && ws.isOpen) {
            isSocketAlreadyOpen.value = true
        } else {
            var userFilter:Double = -1.0
            if (!editText.text.isNullOrEmpty()) {
                userFilter = editText.text.toString().toDouble()
            }
            viewModelScope.launch {
                motiv8aiWSS(userFilter)
            }
        }
    }

    fun onStopButtonClick() {
        if (this::ws.isInitialized) {
            if (ws.isOpen) {
                ws.socket.close()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private suspend fun motiv8aiWSS(userFilter: Double) = withContext(Dispatchers.Default) {
        ws = WebSocketFactory().createSocket(WSS_URL, 5000)
        ws.addListener(object : WebSocketAdapter() {

            override fun onTextMessage(
                websocket: WebSocket?,
                text: String?
            ) {
                super.onTextMessage(websocket, text)

                val grocery = formatTextToGrocery(text)
                val removePrefix = grocery.weight.removeSuffix("kg")
                val weightToDouble = removePrefix.toDouble()
                when {
                    userFilter == -1.0 -> {
                        currentGrocery.postValue(grocery)
                    }
                    weightToDouble > userFilter -> {
                        currentGrocery.postValue(grocery)
                    }
                    else -> {
                        Timber.e("Do Nothing")
                    }
                }
            }

            private fun formatTextToGrocery(text: String?): Grocery {
                return Gson().fromJson<Grocery>(text, Grocery::class.java)
            }

            override fun onCloseFrame(
                websocket: WebSocket?,
                frame: WebSocketFrame?
            ) {
                super.onCloseFrame(websocket, frame)
            }
        })

        ws.connect()
    }


}
