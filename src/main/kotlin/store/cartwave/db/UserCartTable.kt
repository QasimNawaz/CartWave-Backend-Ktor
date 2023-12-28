package store.cartwave.db

import org.jetbrains.exposed.sql.Table

object UserCartTable : Table("user_cart") {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id").references(UserTable.id)
    val productId = integer("product_id").references(ProductTable.id)
    val cartQty = integer("cart_qty")

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(ProductTable.id)
}