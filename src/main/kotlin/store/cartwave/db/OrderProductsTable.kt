package store.cartwave.db

import org.jetbrains.exposed.sql.Table

object OrderProductsTable : Table("order_products") {
    val orderId = integer("order_id").references(OrderTable.id)
    val productId = integer("product_id")
    val title = varchar("title", length = 1000000)
    val price = integer("price")
    val quantity = integer("quantity")
}