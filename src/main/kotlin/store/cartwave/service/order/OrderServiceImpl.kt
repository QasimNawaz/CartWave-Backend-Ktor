package store.cartwave.service.order

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import store.cartwave.config.DATA_INSERTED
import store.cartwave.db.DatabaseFactory.dbQuery
import store.cartwave.db.OrderProductsTable
import store.cartwave.db.OrderTable
import store.cartwave.db.rowToOrder
import store.cartwave.models.Order
import store.cartwave.models.OrderParams

class OrderServiceImpl : OrderService {
    override suspend fun placeOrder(params: OrderParams): String {
        return runCatching {
            dbQuery {
                val orderID = OrderTable.insert {
                    it[userId] = params.userId
                    it[orderDate] = params.orderDate
                    it[shippingAddress] = params.shippingAddress
                    it[promoCode] = params.promoCode
                    it[orderStatus] = params.orderStatus
                    it[totalAmount] = params.totalAmount
                    it[paymentMethod] = params.paymentMethod
                }[OrderTable.id]
                params.products.forEach { product ->
                    OrderProductsTable.insert {
                        it[orderId] = orderID
                        it[productId] = product.productId
                        it[title] = product.title
                        it[price] = product.price
                        it[quantity] = product.quantity
                    }
                }
            }
            DATA_INSERTED
        }.getOrElse {
            it.message.toString()
        }
    }

    override suspend fun getMyOrders(userId: Int): List<Order> {
        return runCatching {
            dbQuery {
                OrderTable.select {
                    OrderTable.userId eq userId
                }.map { row ->
                    row.rowToOrder()
                }
            }
        }.getOrElse {
            emptyList()
        }
    }
}