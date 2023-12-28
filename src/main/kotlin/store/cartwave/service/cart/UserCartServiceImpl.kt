package store.cartwave.service.cart

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import store.cartwave.config.PRODUCT_ADDED_TO_CART
import store.cartwave.config.PRODUCT_ALREADY_ADDED_IN_CART
import store.cartwave.config.PRODUCT_NOT_FOUND_IN_CART
import store.cartwave.config.PRODUCT_REMOVED_FROM_CART
import store.cartwave.db.*
import store.cartwave.db.DatabaseFactory.dbQuery
import store.cartwave.models.Product
import store.cartwave.models.UserCartParams

class UserCartServiceImpl : UserCartService {

    override suspend fun getUserCart(userId: Int): List<Product> {
        return dbQuery {
            (UserCartTable innerJoin ProductTable).select {
                (UserCartTable.userId eq userId) and (UserCartTable.productId eq ProductTable.id)
            }.map { row ->
                row.rowToProductWithQty(userId)
            }
        }.ifEmpty { emptyList<Product>() }
    }

    override suspend fun addToCart(params: UserCartParams): String {
        val existingCart = dbQuery {
            UserCartTable.select {
                (UserCartTable.userId eq params.userId) and (UserCartTable.productId eq params.productId)
            }.firstOrNull()
        }
        val insertCount = if (existingCart == null) {
            dbQuery {
                UserCartTable.insert {
                    it[userId] = params.userId
                    it[productId] = params.productId
                    it[cartQty] = params.cartQty
                }
            }.insertedCount
        } else {
            dbQuery {
                UserCartTable.update({ (UserCartTable.userId eq params.userId) and (UserCartTable.productId eq params.productId) }) {
                    it[cartQty] = params.cartQty
                }
            }
        }
        return if (insertCount > 0) {
            PRODUCT_ADDED_TO_CART
        } else {
            PRODUCT_ALREADY_ADDED_IN_CART
        }
    }

    override suspend fun removeFromCart(params: UserCartParams): String {
        val deletedCount = dbQuery {
            UserCartTable.deleteWhere {
                (userId eq params.userId) and (productId eq params.productId)
            }
        }
        return if (deletedCount > 0) {
            PRODUCT_REMOVED_FROM_CART
        } else {
            PRODUCT_NOT_FOUND_IN_CART
        }
    }
}