package ernestkoko.superpro.checkinternetconnectivitydemo

import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var mConManager: ConnectivityManager
    private lateinit var mCallback: ConnectivityManager.NetworkCallback
    private lateinit var mAlertDialog: AlertDialog

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mConManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val builder = NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            val networkRequest = builder.build()

        val dialog = AlertDialog.Builder(this@MainActivity)
            .setTitle("No Internet")
            .setMessage("Connection Lost.")
            .setPositiveButton("Ok",{ dialogInterface: DialogInterface, i: Int ->
                finish()
            })
       // dialog.show()




        mCallback = object : ConnectivityManager.NetworkCallback() {

            override fun onLost(network: Network) {
                Log.i("MainAct","Network:Lost")
                super.onLost(network)
                //when lost show the alert dialog

                dialog.show()

            }

            override fun onUnavailable() {
                Log.i("MainAct","Network:Lost")
                super.onUnavailable()
                dialog.show()
            }
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                Log.i("MainAct","Network: Available")
                if (dialog != null){

                }
                }






          //  }
        }
        mConManager.registerNetworkCallback(networkRequest,mCallback)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mConManager.registerDefaultNetworkCallback(object :ConnectivityManager.NetworkCallback(){
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    Log.i("Main", "Network: available")
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    Log.i("Main","Network: Lost2")
                    AlertDialog.Builder(this@MainActivity).setTitle("Main").setMessage("Gain").show()
                }

            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onStop() {
        mConManager.unregisterNetworkCallback(mCallback)
        super.onStop()

    }

}