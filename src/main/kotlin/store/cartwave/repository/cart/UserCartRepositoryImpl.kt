package store.cartwave.repository.cart

import store.cartwave.config.*
import store.cartwave.models.UserCartParams
import store.cartwave.service.cart.UserCartService
import store.cartwave.utils.BaseResponse

class UserCartRepositoryImpl(private val userCartService: UserCartService) : UserCartRepository {
    override suspend fun getUserCartCart(userId: Int): BaseResponse<Any> {
        val response = userCartService.getUserCart(userId)
        return if (response.isNotEmpty()) {
            BaseResponse.SuccessResponse(data = response, message = SUCCESS)
        } else {
            BaseResponse.ErrorResponse(message = NO_DATA_FOUND)
        }
    }

    override suspend fun addToCart(params: UserCartParams): BaseResponse<Any> {
        val response = userCartService.addToCart(params)
        return if (response == PRODUCT_ALREADY_ADDED_IN_CART) {
            BaseResponse.ErrorResponse(message = response)
        } else {
            BaseResponse.SuccessResponse(data = response, message = response)
        }
    }

    override suspend fun removeFromCart(params: UserCartParams): BaseResponse<Any> {
        val response = userCartService.removeFromCart(params)
        return if (response == PRODUCT_NOT_FOUND_IN_CART) {
            BaseResponse.ErrorResponse(message = response)
        } else {
            BaseResponse.SuccessResponse(data = response, message = response)
        }
    }
}