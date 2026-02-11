package cat.deim.asm_32.patinfly.domain.models

import java.util.Date

data class Bike(
    val uuid: String,
    val name: String,
    val type: BikeType,
    val creationDate: Date,
    val lastMaintenanceDate: Date?,
    val isActive: Boolean=false,
    val batteryLvl: Double =100.0,
    val meters: Int=0
)