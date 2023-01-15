package a1735904_1737069.huawei

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.huawei.hms.maps.CameraUpdateFactory
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.MapsInitializer
import com.huawei.hms.maps.SupportMapFragment
import com.huawei.hms.maps.model.BitmapDescriptorFactory
import com.huawei.hms.maps.model.LatLng
import com.huawei.hms.maps.model.Marker
import com.huawei.hms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        setTitle("Charging Stations")
        val countrycode = intent.getStringExtra("COUNTRYCODE")
        val distance = intent.getStringExtra("DISTANCE")
        val latitude = intent.getDoubleExtra("LATITUDE", 0.0)
        val longitude = intent.getDoubleExtra("LONGITUDE", 0.0)
        val distanceunit = "km"
        val maxresults = 50
        val apiKey = 420

        MapsInitializer.initialize(this)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openchargemap.io/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val openChargeMapApi = retrofit.create(OpenChargeMapApi::class.java)
        var call: Call<List<POI>>
        if (latitude != 0.0 && longitude != 0.0) {
            call = openChargeMapApi.getPOIs(
                output = "json",
                maxResults = maxresults,
                latitude = latitude,
                longitude = longitude,
                distance = distance,
                key = apiKey,
                distanceunit = distanceunit
            )
        } else {
            call = openChargeMapApi.getPOIs(
                output = "json",
                countryCode = countrycode,
                maxResults = maxresults,
                key = apiKey,
                distanceunit = distanceunit
            )
        }

        val request = call.request()
        val url = request.url()
        Log.d("API_ENDPOINT", url.toString())

        call.enqueue(object : Callback<List<POI>> {
            override fun onResponse(call: Call<List<POI>>, response: Response<List<POI>>) {
                if (response.isSuccessful) {
                    val poiList = response.body()
                    if (poiList != null) {
                        Log.d("POI", poiList.toString())
                        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                        mapFragment.getMapAsync { huaweiMap ->
                            huaweiMap.isMyLocationEnabled = true
                            huaweiMap.uiSettings.isZoomControlsEnabled = true
                            val cameraTarget = LatLng(latitude, longitude)
                            huaweiMap.moveCamera(CameraUpdateFactory.newLatLng(cameraTarget))

                            for (poi in poiList) {
                                poi.AddressInfo.Latitude?.let { lat ->
                                    poi.AddressInfo.Longitude?.let { lng ->
                                        val marker = huaweiMap.addMarker(
                                            MarkerOptions()
                                                .position(LatLng(lat, lng))
                                                .title(poi.AddressInfo.AddressLine1))
                                        marker.tag = poi
                                        if (poi.UsageTypeID == 1) {
                                            Log.d("POI", "Green markers found")
                                            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.green_electric_pump))
                                        } else {
                                            Log.d("POI", "Orange markers found")
                                            marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.orange_electric_pump))
                                        }
                                    }
                                }
                            }
                            huaweiMap.setOnInfoWindowClickListener(object :
                                HuaweiMap.OnInfoWindowClickListener {
                                override fun onInfoWindowClick(marker: Marker) {
                                    val poi = marker.tag as POI
                                    val intent = Intent(this@MapActivity, DetailActivity::class.java)
                                    intent.putExtra("POI_NAME", poi.AddressInfo.Country.Title)
                                    intent.putExtra("POI_ADDRESS", poi.AddressInfo.AddressLine1)
                                    intent.putExtra("POI_ADDRESS2", poi.AddressInfo.AddressLine2)
                                    intent.putExtra("TOWN", poi.AddressInfo.Town)
                                    intent.putExtra("CITY", poi.AddressInfo.StateOrProvince)
                                    intent.putExtra("POSTCODE", poi.AddressInfo.Postcode)
                                    intent.putExtra("LATITUDE", poi.AddressInfo.Latitude)
                                    intent.putExtra("LONGITUDE", poi.AddressInfo.Longitude)
                                    intent.putExtra("PHONE", poi.AddressInfo.ContactTelephone1)
                                    intent.putExtra("EMAIL",poi.AddressInfo.ContactEmail)
                                    intent.putExtra("URL",poi.AddressInfo.RelatedURL )
                                    startActivity(intent)
                                }
                            })
                        }
                    } else {
                        Log.e("POI", "No POIs found")
                    }
                } else {
                    Log.e("POI", "Error getting POIs: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<POI>>, t: Throwable) {
                Log.e("POI", "Error getting POIs", t)
            }
        })

    }
}