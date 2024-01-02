package store.cartwave.service.favourite

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import store.cartwave.config.PRODUCT_ADDED_TO_FAVOURITE
import store.cartwave.config.PRODUCT_ALREADY_ADDED_IN_FAVOURITE
import store.cartwave.config.PRODUCT_NOT_FOUND_IN_FAVOURITES
import store.cartwave.config.PRODUCT_REMOVED_FROM_FAVOURITES
import store.cartwave.db.DatabaseFactory.dbQuery
import store.cartwave.db.FavouriteTable
import store.cartwave.db.ProductTable
import store.cartwave.db.rowToProduct
import store.cartwave.models.FavouriteParams
import store.cartwave.models.Product

class FavouriteServiceImpl : FavouriteService {
    override suspend fun addToFavourite(params: FavouriteParams): String {
        val insertCount = dbQuery {
            FavouriteTable.insert {
                it[userId] = params.userId
                it[productId] = params.productId
            }
        }
        return if (insertCount.insertedCount > 0) {
            PRODUCT_ADDED_TO_FAVOURITE
        } else {
            PRODUCT_ALREADY_ADDED_IN_FAVOURITE
        }
    }

    override suspend fun removeFromFavourite(params: FavouriteParams): String {
        val deletedCount = dbQuery {
            FavouriteTable.deleteWhere {
                (userId eq params.userId) and (productId eq params.productId)
            }
        }
        return if (deletedCount > 0) {
            PRODUCT_REMOVED_FROM_FAVOURITES
        } else {
            PRODUCT_NOT_FOUND_IN_FAVOURITES
        }
    }

    override suspend fun getAllFavourites(userId: Int, pageNumber: Int, pageSize: Int): List<Product> {
        return dbQuery {
            (FavouriteTable innerJoin ProductTable).select {
                (FavouriteTable.userId eq userId) and (FavouriteTable.productId eq ProductTable.id)
            }.limit(pageSize, offset = ((pageNumber - 1) * pageSize).toLong()).map { row ->
                row.rowToProduct()
            }
        }.ifEmpty { emptyList<Product>() }
    }
}