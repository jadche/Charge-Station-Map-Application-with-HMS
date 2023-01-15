package a1735904_1737069.huawei

data class Connections(
  val ConnectionType: ConnectionType,
  val ConnectionTypeID: Int,
  val Amps: String?,
  val Voltage: String?,
  val CurrentType: CurrentType?,
  val Quantity: Int?,
  val Comments: String?,
  val StatusType: StatusType?,
  val LevelID: Int?,
  val PowerKW: Double?
)