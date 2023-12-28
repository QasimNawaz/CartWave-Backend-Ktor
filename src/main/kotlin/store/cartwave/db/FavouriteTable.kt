package store.cartwave.db

import org.jetbrains.exposed.sql.Table

object FavouriteTable : Table("favourites") {

    val id = integer("id").autoIncrement()
    val userId = integer("user_id").references(UserTable.id)
    val productId = integer("product_id").references(ProductTable.id)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(ProductTable.id)
}