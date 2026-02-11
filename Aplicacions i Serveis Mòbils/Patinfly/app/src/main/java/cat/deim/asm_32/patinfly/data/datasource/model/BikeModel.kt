package cat.deim.asm_32.patinfly.data.datasource.model

import cat.deim.asm_32.patinfly.domain.models.Bike
import com.google.gson.annotations.SerializedName
import java.util.Date

data class BikeModel(
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("bike_type")
    val type: BikeTypeModel,
    @SerializedName("creation_date")
    val creationDate: Date,
    @SerializedName("last_maintenance_date")
    val lastMaintenanceDate: Date?,
    @SerializedName("in_maintenance")
    val inMaintenance: Boolean = false,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("is_deleted")
    val isDeleted: Boolean = false,
    @SerializedName("battery_level")
    val batteryLvl: Double,
    @SerializedName("meters")
    val meters: Int
) {
    fun toDomain(): Bike = Bike(
        uuid = this.uuid,
        name = this.name,
        type = this.type.toDomain(),
        creationDate = this.creationDate,
        lastMaintenanceDate = this.lastMaintenanceDate,
        isActive = this.isActive,
        batteryLvl = this.batteryLvl,
        meters = this.meters
    )

    companion object {
        fun fromDomain(bike: Bike): BikeModel = BikeModel(
            uuid = bike.uuid,
            name = bike.name,
            type = BikeTypeModel.fromDomain(bike.type),
            creationDate = bike.creationDate,
            lastMaintenanceDate = bike.lastMaintenanceDate,
            isActive = bike.isActive,
            batteryLvl = bike.batteryLvl,
            meters = bike.meters
        )
    }
}
