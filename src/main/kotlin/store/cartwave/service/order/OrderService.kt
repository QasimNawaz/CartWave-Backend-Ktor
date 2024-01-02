package store.cartwave.service.order

import store.cartwave.models.Order
import store.cartwave.models.OrderParams

interface OrderService {
    suspend fun placeOrder(params: OrderParams): String
    suspend fun getMyOrders(userId: Int): List<Order>
}