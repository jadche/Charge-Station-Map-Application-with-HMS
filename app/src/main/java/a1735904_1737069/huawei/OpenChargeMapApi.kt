package a1735904_1737069.huawei

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenChargeMapApi {
    @GET("poi/")
    fun getPOIs(
        @Query("output") output: String = "json",
        @Query("countrycode", encoded = true) countryCode: String? = null,
        @Query("maxresults") maxResults: Int,
        @Query("latitude", encoded = true) latitude: Double? = null,
        @Query("longitude", encoded = true) longitude: Double? = null,
        @Query("distance", encoded = true) distance: String? = "5",
        @Query("key") key: Int,
        @Query("distanceunit") distanceunit: String
    ): Call<List<POI>>
}
