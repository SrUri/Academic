package cat.deim.asm_32.patinfly.data.datasource.model

import cat.deim.asm_32.patinfly.domain.models.BikeType

data class BikeTypeModel (
    val uuid: String,
    val name: String,
    val type:String
){
    fun toDomain(): BikeType=BikeType(
        uuid = this.uuid,
        name = this.name,
        type = this.type
    )
    companion object {
        fun fromDomain(bikeType: BikeType): BikeTypeModel= BikeTypeModel(
            uuid = bikeType.uuid,
            name = bikeType.name,
            type = bikeType.type
        )
    }
}
