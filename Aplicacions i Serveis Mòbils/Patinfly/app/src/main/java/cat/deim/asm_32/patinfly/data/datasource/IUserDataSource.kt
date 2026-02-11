package cat.deim.asm_32.patinfly.data.datasource

import cat.deim.asm_32.patinfly.data.datasource.model.UserModel

interface IUserDataSource {
    fun insert(user: UserModel): Boolean
    fun insertOrUpdate(userModel: UserModel): Boolean
    fun getUser(): UserModel?
    fun getById(uuid:String): UserModel?
    fun update(userModel: UserModel): UserModel?
    fun deleteUser(uuid:String): UserModel?
}