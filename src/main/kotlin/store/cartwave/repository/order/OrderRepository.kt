package store.cartwave.repository.order

import store.cartwave.models.OrderParams
import store.cartwave.utils.BaseResponse

interface OrderRepository {
    suspend fun placeOrder(params: OrderParams): BaseResponse<Any>
    suspend fun getMyOrders(userId:Int): BaseResponse<Any>
}