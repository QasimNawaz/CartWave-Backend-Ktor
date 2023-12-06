package store.cartwave.service.auth

import store.cartwave.models.CreateUserParams
import store.cartwave.models.User

interface AuthService {
    suspend fun registerUser(params: CreateUserParams): User?
    suspend fun loginUser(email: String, password: String): User?
    suspend fun findUserByEmail(email: String): User?
}