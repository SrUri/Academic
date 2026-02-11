package cat.deim.asm_32.patinfly.data.repository

import cat.deim.asm_32.patinfly.data.datasource.IBikeDataSource
import cat.deim.asm_32.patinfly.data.datasource.model.BikeModel
import cat.deim.asm_32.patinfly.domain.models.Bike
import cat.deim.asm_32.patinfly.domain.repository.IBikeRepository

class BikeRepository (private val bikeLocalDataSource: IBikeDataSource):IBikeRepository{

    override fun insert(bike: Bike): Boolean {
        return bikeLocalDataSource.insert(BikeModel.fromDomain(bike))
    }
    override fun getAll(): Collection<Bike> {
        return bikeLocalDataSource.getAll().map { it.toDomain() }
    }
    override fun update(bike: Bike): Boolean {
        return bikeLocalDataSource.update(BikeModel.fromDomain(bike))!=null
    }
    override fun getById(uuid: String): Bike? {
        return bikeLocalDataSource.getById(uuid)?.toDomain()
    }
    override fun delete(uuid: String): Boolean {
        return bikeLocalDataSource.delete(uuid)
    }
}