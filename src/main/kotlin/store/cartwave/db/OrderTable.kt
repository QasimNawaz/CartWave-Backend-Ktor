package store.cartwave.db

import org.jetbrains.exposed.sql.Table

object OrderTable : Table("orders") {

    val id = integer("id").autoIncrement()
    val userId = integer("user_id").references(UserTable.id)
    val orderDate = varchar("order_date", 100)
    val shippingAddress = varchar("shipping_address", 10000)
    val promoCode = varchar("promo_code", 100)
    val orderStatus = varchar("order_status", 100)
    val totalAmount = integer("total_Amount")
    val paymentMethod = varchar("payment_method", 100)

    override val primaryKey = PrimaryKey(id)
}