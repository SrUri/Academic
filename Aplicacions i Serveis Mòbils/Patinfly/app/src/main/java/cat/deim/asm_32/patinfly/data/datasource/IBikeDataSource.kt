package cat.deim.asm_32.patinfly.data.datasource

import cat.deim.asm_32.patinfly.data.datasource.model.BikeModel

interface IBikeDataSource {
    fun insert(bike: BikeModel): Boolean
    fun insertOrUpdate(bike: BikeModel): Boolean
    fun getAll(): List<BikeModel>
    fun getById(uuid: String): BikeModel?
    fun update(bike: BikeModel): BikeModel?
    fun delete(uuid: String): Boolean
}