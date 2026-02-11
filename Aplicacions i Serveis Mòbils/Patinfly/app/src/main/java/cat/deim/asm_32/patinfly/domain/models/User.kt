package cat.deim.asm_32.patinfly.domain.models

import java.util.*

data class User(
    val uuid: String,
    val name: String,
    val email: String,
    val hashedPassword: String,
    val creationDate: Date,
    val lastConnection: Date,
    val deviceId: String
)