package store.cartwave.db

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object ProductTable : Table("products") {
    val id = integer("id").autoIncrement()
    val actualPrice = varchar("actual_price", 20)
    val averageRating = varchar("average_rating", 100)
    val brand = varchar("brand", 1000000)
    val category = varchar("category", 1000000)
    val crawledAt = varchar("crawled_at", 1000000)
    val description = varchar("description", 1000000)
    val discount = varchar("discount", 20)
    val images = varchar("images", 1000000).nullable()
    val outOfStock: Column<Boolean> = bool("out_of_stock")
    val pid = varchar("pid", 1000000)
    val productDetails = varchar("product_details", 1000000).nullable()
    val seller = varchar("seller", 1000000)
    val sellingPrice = varchar("selling_price", 20)
    val subCategory = varchar("sub_category", 1000000)
    val title = varchar("title", 1000000)
    val url = varchar("url", 1000000)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}