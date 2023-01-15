package a1735904_1737069.huawei

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val poiName = intent.getStringExtra("POI_NAME")
        val poiAddress = intent.getStringExtra("POI_ADDRESS")
        val poiAddress2 = intent.getStringExtra("POI_ADDRESS2")
        val town = intent.getStringExtra("TOWN")
        val city = intent.getStringExtra("CITY")
        val postcode = intent.getStringExtra("POSTCODE")
        val latitude = intent.getDoubleExtra("LATITUDE", 0.0)
        val longitude = intent.getDoubleExtra("LONGITUDE", 0.0)
        val phone = intent.getStringExtra("PHONE")
        val email = intent.getStringExtra("EMAIL")
        val urltext = intent.getStringExtra("URL")


        val fullAddressTextView = findViewById<TextView>(R.id.fullAddress)
        fullAddressTextView.text = poiAddress + ", " + poiAddress2 + ", " + "\n" + town + ", " + city + ", " + postcode + ", " + poiName
        val latLngTextView = findViewById<TextView>(R.id.latLng)
        latLngTextView.text = latitude.toString() + ", " + longitude.toString()
        val phoneTextView = findViewById<TextView>(R.id.phoneText)
        phoneTextView.text = phone
        val emailTextView = findViewById<TextView>(R.id.emailText)
        emailTextView.text = email
        val myurlTextView = findViewById<TextView>(R.id.urlText)
        myurlTextView.text = urltext
    }
}