package a1735904_1737069.huawei

data class AddressInfo(
  val Title: String?,
  val AddressLine1: String?,
  val AddressLine2: String?,
  val Town: String?,
  val StateOrProvince: String?,
  val Postcode: String?,
  val Country: Country,
  val Latitude: Double?,
  val Longitude: Double?,
  val ContactTelephone1: String?,
  val ContactEmail: String?,
  val RelatedURL: String?
)