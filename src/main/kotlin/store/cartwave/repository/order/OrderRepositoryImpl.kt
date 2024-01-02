package store.cartwave.repository.order

import store.cartwave.config.DATA_INSERTED
import store.cartwave.config.NO_DATA_FOUND
import store.cartwave.config.SUCCESS
import store.cartwave.models.OrderParams
import store.cartwave.service.order.OrderService
import store.cartwave.utils.BaseResponse

class OrderRepositoryImpl(private val orderService: OrderService) : OrderRepository {
    override suspend fun placeOrder(params: OrderParams): BaseResponse<Any> {
        val response = orderService.placeOrder(params)
        return if (response == DATA_INSERTED) {
            BaseResponse.SuccessResponse(data = response, message = response)
        } else {
            BaseResponse.ErrorResponse(message = response)
        }
    }

    override suspend fun getMyOrders(userId: Int): BaseResponse<Any> {
        val response = orderService.getMyOrders(userId)
        return if (response.isEmpty()) {
            BaseResponse.ErrorResponse(message = NO_DATA_FOUND)
        } else {
            BaseResponse.SuccessResponse(data = response, message = SUCCESS)
        }
    }
}