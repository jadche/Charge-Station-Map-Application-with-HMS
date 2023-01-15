package a1735904_1737069.huawei

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.hbb20.CountryCodePicker
import com.huawei.agconnect.auth.AGConnectAuth
import com.huawei.hms.location.LocationRequest
import com.huawei.hms.location.LocationServices

class SearchActivity : AppCompatActivity() {

    private lateinit var latitudeTextView: TextView
    private lateinit var longitudeTextView: TextView
    private lateinit var locationButton: TextView
    private lateinit var searchButton: TextView
    var latitude: Double? = null
    var longitude: Double? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setTitle("Search Charging Stations")

        val userName = intent.getStringExtra("USER_NAME")
        val greetingTextView = findViewById<TextView>(R.id.greeting_text_view)
        greetingTextView.text = "Hi, $userName!"

        // Dynamically apply for required permissions if the API level is 28 or smaller.
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            Log.i(TAG, "android sdk <= 28 Q")
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                val strings = arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                ActivityCompat.requestPermissions(this, strings, 1)
            }
        } else {
            // Dynamically apply for required permissions if the API level is greater than 28. The android.permission.ACCESS_BACKGROUND_LOCATION permission is required.
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    "android.permission.ACCESS_BACKGROUND_LOCATION"
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                val strings = arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    "android.permission.ACCESS_BACKGROUND_LOCATION"
                )
                ActivityCompat.requestPermissions(this, strings, 2)
            }
        }

        latitudeTextView = findViewById(R.id.textViewLatitude)
        longitudeTextView = findViewById(R.id.textViewLongitude)
        locationButton = findViewById(R.id.location_button)
        searchButton = findViewById(R.id.search_button)

        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.numUpdates = 1

        locationButton.setOnClickListener {
            val client = LocationServices.getFusedLocationProviderClient(this)
            client.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    latitude = location.latitude
                    longitude = location.longitude
                    latitudeTextView.text = "Latitude: ${String.format("%.2f",location.latitude)}"
                    longitudeTextView.text = "Longitude: ${String.format("%.2f",location.longitude)}"
                }
            }
        }
        searchButton.setOnClickListener {
            val countryCodePicker = findViewById<CountryCodePicker>(R.id.ccp)
            val distanceEditText = findViewById<EditText>(R.id.editDistance)
            val countrycode = countryCodePicker.selectedCountryNameCode
            var distance = distanceEditText.text.toString()

            val intent = Intent(this, MapActivity::class.java)
            intent.putExtra("COUNTRYCODE", countrycode)
            if (distance.isNullOrEmpty()) {
                intent.putExtra("DISTANCE", "5")
            } else {
                intent.putExtra("DISTANCE", distance)
            }
            intent.putExtra("LATITUDE", latitude)
            intent.putExtra("LONGITUDE", longitude)
            startActivity(intent)
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                AGConnectAuth.getInstance().signOut()
                val sharedPreferences = getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE)
                sharedPreferences.edit().putBoolean("SIGNED_OUT", true).apply()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun onClearButtonClick(view: View) {
        latitude = null
        latitudeTextView.text = "Latitude:"
        longitude= null
        longitudeTextView.text = "Longitude:"
    }
}
