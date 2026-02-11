package cat.deim.asm_32.patinfly.domain.repository

import cat.deim.asm_32.patinfly.domain.models.User

interface IUserRepository {
    fun setUser(user: User): Boolean
    fun getUser(): User?
    fun getById(uuid: String): User?
    fun updateUser(user: User): User?
    fun deleteUser(uuid:String): User?
}