package cat.deim.asm_32.patinfly.data.repository

import cat.deim.asm_32.patinfly.data.datasource.IUserDataSource
import cat.deim.asm_32.patinfly.data.datasource.model.UserModel
import cat.deim.asm_32.patinfly.domain.models.User
import cat.deim.asm_32.patinfly.domain.repository.IUserRepository

class UserRepository (private val userDataSource: IUserDataSource):IUserRepository{
    override fun setUser(user: User): Boolean {
        return userDataSource.insert(UserModel.fromDomain(user))
    }
    override fun getUser(): User? {
        return userDataSource.getUser()?.toDomain()
    }
    override fun updateUser(user: User): User? {
        return userDataSource.update(UserModel.fromDomain(user))?.toDomain()
    }
    override fun getById(uuid: String): User? {
        return userDataSource.getById(uuid)?.toDomain()
    }
    override fun deleteUser(uuid:String): User? {
        return userDataSource.deleteUser(uuid)?.toDomain()
    }
}

