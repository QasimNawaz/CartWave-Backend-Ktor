package store.cartwave.repository.auth

import store.cartwave.models.CreateUserParams
import store.cartwave.models.UserLoginParams
import store.cartwave.utils.BaseResponse

interface AuthRepository {
    suspend fun registerUser(params: CreateUserParams): BaseResponse<Any>
    suspend fun loginUser(params: UserLoginParams): BaseResponse<Any>
}