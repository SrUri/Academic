package cat.deim.asm_32.patinfly.domain.usecase

import cat.deim.asm_32.patinfly.domain.repository.IUserRepository

class LoginUseCase(private val userRepository: IUserRepository) {
    fun execute(email: String, password: String): Boolean {
        val user = userRepository.getUser() ?: return false
        return user.email == email && user.hashedPassword == password
    }
}