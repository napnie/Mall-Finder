package com.example.napni.mallfinder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private val ERROR_DIALOG_REQUEST = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun btnClicked(view: View) {
        val intent = Intent(this, MapsActivity::class.java )
        if( isServicesOK() ) {
            startActivity(intent)
        }
    }

    fun isServicesOK(): Boolean {
        Log.d(TAG, "isServicesOK: checking google services version")

        val available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this@MainActivity)

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working")
            return true
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it")
            val dialog = GoogleApiAvailability.getInstance().getErrorDialog(this@MainActivity, available, ERROR_DIALOG_REQUEST)
            dialog.show()
        } else {
            Toast.makeText(this@MainActivity, "You can't make map requests", Toast.LENGTH_SHORT).show()
        }
        return false
    }
}
