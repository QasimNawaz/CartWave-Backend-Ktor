package store.cartwave.repository.cart

import store.cartwave.models.UserCartParams
import store.cartwave.utils.BaseResponse

interface UserCartRepository {
    suspend fun getUserCartCart(userId: Int): BaseResponse<Any>
    suspend fun addToCart(params: UserCartParams): BaseResponse<Any>
    suspend fun removeFromCart(params: UserCartParams): BaseResponse<Any>
}